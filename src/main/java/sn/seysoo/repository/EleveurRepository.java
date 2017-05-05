package sn.seysoo.repository;

import sn.seysoo.domain.Eleveur;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Eleveur entity.
 */
@SuppressWarnings("unused")
public interface EleveurRepository extends MongoRepository<Eleveur,String> {

}
