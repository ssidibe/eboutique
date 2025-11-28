package ml.finances.caisff.eboutique.repositories;


import ml.finances.caisff.eboutique.entities.Boutique;
import ml.finances.caisff.eboutique.entities.Magasin;

import java.util.List;

public interface MagasinRepository {

    void creer(Magasin magasin);

    Magasin getById(Short id);

    Magasin getByNom(String nom);

    List<Magasin> search(String txt);

    List<Magasin> getMagasins();

    Magasin update(Magasin magasin);

    void delete(Magasin magasin);


    List<Boutique> findAllBoutique();

    List<Boutique> findMagasinBoutiques(Magasin magasin);

    List<Boutique> findMagasinBoutiques(Short magasinId);

}
