package ml.finances.caisff.eboutique.services;

import ml.finances.caisff.eboutique.entities.Boutique;
import ml.finances.caisff.eboutique.repositories.BoutiqueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoutiqueService {

    private final BoutiqueRepository boutiqueRepository;

    public BoutiqueService(BoutiqueRepository boutiqueRepository) {
        this.boutiqueRepository = boutiqueRepository;
    }

    public List<Boutique> getAllBoutiques() {
        return boutiqueRepository.findAll();
    }

    public Boutique findBoutique(Short id) throws MyApiException {
        Optional<Boutique> boutique = boutiqueRepository.findById(id);
        if (boutique.isPresent()) {
            return boutique.get();
        }else{
            throw new MyApiException(444, "La boutique n'existe pas");
        }
    }

    public Boutique create(Boutique boutique) throws MyApiException {
        if (boutique.getId() != null) {
            throw  new MyApiException(451, "L'id ne doit pas etre present pour creer une boutique");
        }

        if(boutique.getNom()==null || boutique.getNom().isBlank()){
            throw new MyApiException(452,"Le nom de la boutique est obligatoire");
        }

        Optional<Boutique> bDb=boutiqueRepository.findByNom(boutique.getNom());
        if(bDb.isPresent()){
            throw new MyApiException(453, "Il y'a deja une boutique avec le meme nom");
        }

        return boutiqueRepository.save(boutique);
    }
}
