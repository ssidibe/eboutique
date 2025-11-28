package ml.finances.caisff.eboutique.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import ml.finances.caisff.eboutique.entities.Boutique;
import ml.finances.caisff.eboutique.entities.Magasin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MagasinRepositoryImpl implements MagasinRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void creer(Magasin magasin) {
        em.persist(magasin);
    }

    @Override
    public Magasin getById(Short id) {
        return em.find(Magasin.class, id);
    }

    @Override
    public Magasin getByNom(String nom) {
        return null;
    }

    @Override
    public List<Magasin> search(String txt) {
        TypedQuery<Magasin> query = em.createNamedQuery("Magasin.search", Magasin.class);
        query.setParameter("txt", '%'+txt+'%');
        return query.getResultList();
    }

    @Override
    public List<Magasin> getMagasins() {
        return List.of();
    }

    @Override
    @Transactional
    public Magasin update(Magasin magasin) {
        return em.merge(magasin);
    }

    @Override
    @Transactional
    public void delete(Magasin magasin) {
        // controle
        deleteById(magasin.getId());
    }

    private void deleteById(Short id) {

    }

    @Override
    public List<Boutique> findAllBoutique() {
        return List.of();
    }

    @Override
    public List<Boutique> findMagasinBoutiques(Magasin magasin) {
        return List.of();
    }

    @Override
    public List<Boutique> findMagasinBoutiques(Short magasinId) {
        return List.of();
    }
}
