package sn.seysoo.repository;

import sn.seysoo.domain.Adresse;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Adresse entity.
 */
@SuppressWarnings("unused")
public interface AdresseRepository extends MongoRepository<Adresse,String> {

}
