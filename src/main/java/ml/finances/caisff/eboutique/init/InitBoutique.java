package ml.finances.caisff.eboutique.init;

import ml.finances.caisff.eboutique.entities.Boutique;
import ml.finances.caisff.eboutique.entities.Magasin;
import ml.finances.caisff.eboutique.repositories.BoutiqueRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class InitBoutique implements CommandLineRunner {

    private final BoutiqueRepository boutiqueRepository;

    public InitBoutique(BoutiqueRepository boutiqueRepository) {
        this.boutiqueRepository = boutiqueRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(boutiqueRepository.count() == 0) {
            Boutique boutique = new Boutique();
            boutique.setNom("B1 nom");
            boutique.setDescription("B1 desc");
            boutiqueRepository.save(boutique);

            Boutique boutique2 = new Boutique();
            boutique2.setNom("B2 nom");
            boutique2.setDescription("B2 nom desc");
            boutiqueRepository.save(boutique2);
        }

    }
}
