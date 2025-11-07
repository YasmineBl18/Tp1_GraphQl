package org.example.tp1graphql;

import org.example.tp1graphql.enums.Genre;
import org.example.tp1graphql.model.Centre;
import org.example.tp1graphql.model.Etudiant;
import org.example.tp1graphql.repositories.CentreRepository;
import org.example.tp1graphql.repositories.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Tp1GraphQlApplication implements CommandLineRunner {
    @Autowired
    EtudiantRepository etudiantRepository;
    @Autowired
    CentreRepository centreRepository;
    public static void main(String[] args) {
        SpringApplication.run(Tp1GraphQlApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        Centre centre1 = Centre.builder().nom("Maarif").adresse("Roudani").build();
        centreRepository.save(centre1);

        Centre centre2 = Centre.builder().nom("Oulfa").adresse("Orangers").build();
        centreRepository.save(centre2);


        Etudiant et1= Etudiant.builder().nom("Adnani").prenom("Adnani").genre(Genre.Homme)
                .centre(centre1).build();
        etudiantRepository.save(et1);
    }



}
