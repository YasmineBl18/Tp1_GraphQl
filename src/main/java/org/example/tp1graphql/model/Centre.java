package org.example.tp1graphql.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Centre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nom;
    private String adresse;

    @OneToMany(mappedBy = "centre", fetch = FetchType.LAZY)
    @ToString.Exclude          // IMPORTANT pour éviter les boucles
    @EqualsAndHashCode.Exclude
    private List<Etudiant> listEtudiants;
}
