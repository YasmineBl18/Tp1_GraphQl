package org.example.tp1graphql.repositories;

import org.example.tp1graphql.model.Centre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CentreRepository extends JpaRepository<Centre,Long>
{
}