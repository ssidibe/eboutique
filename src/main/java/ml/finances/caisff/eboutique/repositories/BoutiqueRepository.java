package ml.finances.caisff.eboutique.repositories;

import ml.finances.caisff.eboutique.entities.Boutique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoutiqueRepository extends JpaRepository<Boutique, Short> {

    Optional<Boutique> findByNom(String nom);

    @Query("select distinct m.boutique from Magasin m where m.boutique.nom like :txt or m.nom like :txt")
    List<Boutique> search(@Param("txt") String txt);
}