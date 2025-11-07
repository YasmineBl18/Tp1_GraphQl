package org.example.tp1graphql.dtos;

import org.example.tp1graphql.enums.Genre;

public record EtudiantDTO (
        String nom,
        String prenom,
        Genre genre,
        Long centreId
){}
