package ml.finances.caisff.eboutique.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class Demarrage implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("#######   Demarrage du serveur #####");
        System.out.println("#######   envoi mail notification #####");
    }
}
