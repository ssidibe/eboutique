package ml.finances.caisff.eboutique.init;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ml.finances.caisff.eboutique.entities.Boutique;
import ml.finances.caisff.eboutique.entities.Magasin;
import ml.finances.caisff.eboutique.repositories.BoutiqueRepository;
import ml.finances.caisff.eboutique.repositories.MagasinRepository;
import ml.finances.caisff.eboutique.repositories.MagasinRepositoryImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Order(2)
@Profile({"test", "dev"})
public class InitMagasin implements CommandLineRunner {

    private final BoutiqueRepository boutiqueRepository;
    @PersistenceContext
    private EntityManager em;


    private final MagasinRepository magasinRepository;

    public InitMagasin(MagasinRepository magasinRepository, BoutiqueRepository boutiqueRepository) {
        this.magasinRepository = magasinRepository;
        this.boutiqueRepository = boutiqueRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("###### init magazin #####");
        Optional<Boutique> dbBoutique=boutiqueRepository.findById((short)1);
        if(dbBoutique.isPresent() && false) {
            Boutique boutique=dbBoutique.get();
            Magasin m1 = new Magasin();
            m1.setNom("Bamako Shop");
            m1.setDescription("Aci 2000");
            m1.setBoutique(boutique);
            magasinRepository.creer(m1);

            Magasin m2 = new Magasin();
            m2.setNom("Shoes shopping services");
            m2.setDescription("ville");
            m2.setBoutique(boutique);
            magasinRepository.creer(m2);

            Magasin m3 = new Magasin();
            m3.setNom("Comptoir de Bamako");
            m3.setDescription("rue 10");
            m3.setBoutique(boutique);
            magasinRepository.creer(m3);
        }


    }
}
