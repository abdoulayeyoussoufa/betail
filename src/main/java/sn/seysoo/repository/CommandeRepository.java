package sn.seysoo.repository;

import sn.seysoo.domain.Commande;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Commande entity.
 */
@SuppressWarnings("unused")
public interface CommandeRepository extends MongoRepository<Commande,String> {

}
