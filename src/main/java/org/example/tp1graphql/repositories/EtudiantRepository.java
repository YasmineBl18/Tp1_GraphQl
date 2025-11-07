package org.example.tp1graphql.repositories;

import org.example.tp1graphql.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant,Long>
{
}