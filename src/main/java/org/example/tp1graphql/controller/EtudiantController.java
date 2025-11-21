package org.example.tp1graphql.controller;

import org.example.tp1graphql.dtos.EtudiantDTO;
import org.example.tp1graphql.model.Centre;
import org.example.tp1graphql.model.Etudiant;
import org.example.tp1graphql.repositories.CentreRepository;
import org.example.tp1graphql.repositories.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;

@Controller
public class EtudiantController {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private CentreRepository centreRepository;

    //  Sink pour les AJOUTS
    private final Sinks.Many<Etudiant> sinkAdd =
            Sinks.many().multicast().onBackpressureBuffer();

    //  Sink pour les SUPPRESSIONS
    private final Sinks.Many<Long> sinkDelete =
            Sinks.many().multicast().onBackpressureBuffer();


    /* =======================
            QUERIES
       ======================= */

    @QueryMapping
    public List<Etudiant> listEtudiants() {
        return etudiantRepository.findAll();
    }

    @QueryMapping
    public Etudiant getEtudiantById(@Argument Float id) {
        return etudiantRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("Étudiant " + id + " non trouvé"));
    }

    @QueryMapping
    public List<Centre> centres() {
        return centreRepository.findAll();
    }

    @QueryMapping
    public Centre getCentreById(@Argument Float id) {
        return centreRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("Centre " + id + " non trouvé"));
    }


    /* =======================
          MUTATIONS
       ======================= */

    @MutationMapping
    public Etudiant addEtudiant(@Argument("etudiant") EtudiantDTO dto) {

        Centre centre = centreRepository.findById(dto.centreId().longValue())
                .orElse(null);

        Etudiant et = new Etudiant();
        et.setNom(dto.nom());
        et.setPrenom(dto.prenom());
        et.setGenre(dto.genre());
        et.setCentre(centre);

        Etudiant saved = etudiantRepository.save(et);

        //  Pousser l'événement d'ajout
        sinkAdd.tryEmitNext(saved);

        return saved;
    }

    @MutationMapping
    public Etudiant updateEtudiant(@Argument Float id,
                                   @Argument("etudiant") EtudiantDTO dto) {

        Etudiant et = etudiantRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("Étudiant " + id + " non trouvé"));

        Centre centre = centreRepository.findById(dto.centreId().longValue())
                .orElse(null);

        et.setNom(dto.nom());
        et.setPrenom(dto.prenom());
        et.setGenre(dto.genre());
        et.setCentre(centre);

        return etudiantRepository.save(et);
    }

    @MutationMapping
    public String deleteEtudiant(@Argument Float id) {

        if (!etudiantRepository.existsById(id.longValue())) {
            return "Étudiant " + id + " n'existe pas";
        }

        etudiantRepository.deleteById(id.longValue());

        //  Pousser l'événement de suppression
        sinkDelete.tryEmitNext(id.longValue());

        return "Étudiant " + id + " bien supprimé";
    }


    /* =======================
         SUBSCRIPTIONS
       ======================= */

    @SubscriptionMapping
    public Flux<Etudiant> etudiantAdded() {
        return sinkAdd.asFlux();
    }

    @SubscriptionMapping
    public Flux<Long> etudiantDeleted() {
        return sinkDelete.asFlux();
    }
}
