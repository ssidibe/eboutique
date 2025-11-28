package ml.finances.caisff.eboutique.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "boutique")
public class Boutique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    private String nom;

    private String description;

    @JsonIgnore
    private Integer nbreMagasins=5;

    @JsonIgnore
    @OneToMany(mappedBy = "boutique")
    List<Magasin> magasins;
}