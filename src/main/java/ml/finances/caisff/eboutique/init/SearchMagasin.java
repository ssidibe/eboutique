package ml.finances.caisff.eboutique.init;

import ml.finances.caisff.eboutique.entities.Magasin;
import ml.finances.caisff.eboutique.repositories.MagasinRepositoryImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(3)
public class SearchMagasin implements CommandLineRunner {
    private final MagasinRepositoryImpl magasinRepositoryImpl;

    public SearchMagasin(MagasinRepositoryImpl magasinRepositoryImpl) {
        this.magasinRepositoryImpl = magasinRepositoryImpl;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Search Magasin");

        new Thread(()->{
            try {
                Thread.sleep(5000);
                List<Magasin> res=magasinRepositoryImpl.search("a");
                res.forEach((p)->{
                    System.out.println("magazin trouvé"+p.getNom());
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("fin traitement search");
        }).start();

        System.out.println("Fin search Magasin");
    }
}
