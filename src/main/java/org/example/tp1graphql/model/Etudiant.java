package org.example.tp1graphql.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.tp1graphql.enums.Genre;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "etudiants")
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom_etudiant", nullable = false)
    private String nom;

    @Column(name = "prenom_etudiant")
    private String prenom;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centre_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Centre centre;
}
