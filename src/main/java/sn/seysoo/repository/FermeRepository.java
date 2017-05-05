package sn.seysoo.repository;

import sn.seysoo.domain.Ferme;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Ferme entity.
 */
@SuppressWarnings("unused")
public interface FermeRepository extends MongoRepository<Ferme,String> {

}
