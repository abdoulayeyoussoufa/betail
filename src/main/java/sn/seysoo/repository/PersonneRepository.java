package sn.seysoo.repository;

import sn.seysoo.domain.Personne;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Personne entity.
 */
@SuppressWarnings("unused")
public interface PersonneRepository extends MongoRepository<Personne,String> {

}
