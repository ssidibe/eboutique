package ml.finances.caisff.eboutique.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "Magasin.findAll", query = "select m from Magasin m"),
        @NamedQuery(name = "Magasin.findByNom", query = "select m from Magasin m where m.nom= :nom"),
        @NamedQuery(name = "Magasin.search", query = "select m from Magasin m where m.nom like :txt or m.description like :txt"),
})
public class Magasin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id; // Byte Short Integer Long
    @Column(nullable = false, unique = true)
    private String nom;
    private String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Boutique boutique;

}
