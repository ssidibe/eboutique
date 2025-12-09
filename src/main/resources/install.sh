#!/bin/bash

### ===========================================================
###   INSTALLATION : ELK 9 + APACHE2 + SSL LET'S ENCRYPT
###   + LOGSTASH SPRING CLOUD PIPELINE (JSON logs)
###   Debian 10/11/12
### ===========================================================

KIBANA_DOMAIN="kibana.e-jago.com"
ES_DOMAIN="es.e-jago.com"

echo "=== Mise à jour du système ==="
sudo apt update
sudo apt upgrade


echo "=== Installation dépendances ==="
sudo apt-get install gpg curl apt-transport-https

### ------------------------------------------------------------
### 1. Installation du dépôt Elastic 9.x
### ------------------------------------------------------------
echo "=== Ajout clé GPG Elastic ==="
sudo wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo gpg --dearmor -o /usr/share/keyrings/elasticsearch-keyring.gpg

echo "deb [signed-by=/usr/share/keyrings/elasticsearch-keyring.gpg] https://artifacts.elastic.co/packages/9.x/apt stable main" \
| sudo tee /etc/apt/sources.list.d/elastic-9.x.list


### ------------------------------------------------------------
### 2. Elasticsearch 9
### ------------------------------------------------------------
echo "=== Installation Elasticsearch 9 ==="
sudo apt-get update && sudo apt-get install elasticsearch

sudo nano /etc/elasticsearch/elasticsearch.yml

#node.name: node-1
#http.port: 9200
#network.host: 0.0.0.0
#discovery.seed_hosts: ["127.0.0.1"]
#cluster.initial_master_nodes: ["node-1"]
#xpack.security.enrollment.enabled: false
#EOF

sudo systemctl daemon-reload
sudo systemctl enable elasticsearch
sudo systemctl start elasticsearch
sleep 10
curl -k http://localhost:9200
#bZg*x=q34YFclEtwJOnj
sudo /usr/share/elasticsearch/bin/elasticsearch-reset-password -u elastic
curl -u elastic:bZg*x=q34YFclEtwJOnj -k https://localhost:9200

### ------------------------------------------------------------
### 3. Kibana 9
### ------------------------------------------------------------
echo "=== Installation Kibana 9 ==="
apt install kibana

cat <<EOF > /etc/kibana/kibana.yml
server.host: "0.0.0.0"
server.port: 5601
elasticsearch.hosts: ["http://localhost:9200"]
server.ssl.enabled: false
EOF

#sudo /usr/share/elasticsearch/bin/elasticsearch-service-tokens create elastic/kibana kibana-token

systemctl enable kibana
systemctl start kibana

### ------------------------------------------------------------
### 4. Logstash 9 + Pipeline Spring Cloud
### ------------------------------------------------------------
echo "=== Installation Logstash 9 ==="
apt install -y logstash

echo "=== Création du pipeline Logstash spécial Spring Cloud ==="

cat <<EOF > /etc/logstash/conf.d/spring-pipeline.conf
input {
  tcp {
    port => 5000
    codec => json
  }
}

filter {
  mutate {
    rename => { "service" => "service.name" }
    rename => { "trace" => "trace.id" }
    rename => { "span" => "span.id" }
  }
}

output {
  elasticsearch {
    hosts => ["http://127.0.0.1:9200"]
    index => "spring-%{+YYYY.MM.dd}"
  }
}
EOF

systemctl enable logstash
systemctl start logstash
sudo ss -tulnp | grep logstash


### ------------------------------------------------------------
### 5. Apache2 + Reverse Proxy
### ------------------------------------------------------------
echo "=== Installation Apache2 ==="
apt install -y apache2

echo "=== Activation modules proxy ==="
a2enmod proxy proxy_http ssl rewrite headers
systemctl restart apache2

### ------------------------------------------------------------
### 6. VirtualHost Apache pour Kibana (HTTP avant SSL)
### ------------------------------------------------------------
echo "=== Configuration Apache Kibana VirtualHost ==="

cat <<EOF > /etc/apache2/sites-available/kibana.conf
<VirtualHost *:80>
    ServerName $KIBANA_DOMAIN
    ProxyPreserveHost On
    ProxyPass / http://127.0.0.1:5601/
    ProxyPassReverse / http://127.0.0.1:5601/
</VirtualHost>
EOF

a2ensite kibana
systemctl reload apache2

### ------------------------------------------------------------
### 7. VirtualHost Apache pour Elasticsearch (HTTP avant SSL)
### ------------------------------------------------------------
echo "=== Configuration Apache Elasticsearch VirtualHost ==="

cat <<EOF > /etc/apache2/sites-available/elasticsearch.conf
<VirtualHost *:80>
    ServerName $ES_DOMAIN
    ProxyPreserveHost On
    ProxyPass / http://127.0.0.1:9200/
    ProxyPassReverse / http://127.0.0.1:9200/
</VirtualHost>
EOF

a2ensite elasticsearch
systemctl reload apache2

### ------------------------------------------------------------
### 8. Installation Let's Encrypt (Certbot Apache)
### ------------------------------------------------------------
echo "=== Installation Certbot + plugin Apache ==="
apt install -y certbot python3-certbot-apache

### ------------------------------------------------------------
### 9. Génération certificats SSL
### ------------------------------------------------------------
echo "=== Génération certificat SSL Kibana ==="
certbot --apache -d $KIBANA_DOMAIN --non-interactive --agree-tos -m sambasidibe@gmail.com

echo "=== Génération certificat SSL Elasticsearch ==="
certbot --apache -d $ES_DOMAIN --non-interactive --agree-tos -m sambasidibe@gmail.com

### ------------------------------------------------------------
### 10. Fin
### ------------------------------------------------------------
echo "======================================================="
echo "  INSTALLATION COMPLETE — ELK 9 + HTTPS + Logstash OK   "
echo "======================================================="
echo ""
echo "Accéder à Kibana :   https://$KIBANA_DOMAIN"
echo "Accéder à Elastic :  https://$ES_DOMAIN"
echo "Logstash écoute sur : port 5000 (TCP JSON)"
echo ""
echo "Pipeline Logstash :  /etc/logstash/conf.d/spring-pipeline.conf"
echo "Fichier Kibana :     /etc/kibana/kibana.yml"
echo "Fichier Elasticsearch: /etc/elasticsearch/elasticsearch.yml"
echo ""
echo "======================================================="
echo "                TOUT EST OPÉRATIONNEL 🎉               "
echo "======================================================="
