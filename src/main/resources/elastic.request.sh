#creer un index
curl -X PUT -H 'Content-Type: application/json' -i 'http://52.91.56.208:9200/produits' --data '{
  "mappings": {
    "properties": {
      "id":            { "type": "long" },
      "nom":           { "type": "text" },
      "description":   { "type": "text" },
      "vendeurId":     { "type": "long" },
      "tags":          { "type": "keyword" },
      "created":       { "type": "date" }
    }
  }
}'

#supprimmer l'index
curl -X DELETE -i 'http://52.91.56.208:9200/produits'