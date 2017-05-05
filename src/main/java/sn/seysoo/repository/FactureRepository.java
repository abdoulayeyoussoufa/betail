package sn.seysoo.repository;

import sn.seysoo.domain.Facture;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Facture entity.
 */
@SuppressWarnings("unused")
public interface FactureRepository extends MongoRepository<Facture,String> {

}
