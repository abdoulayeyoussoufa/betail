package sn.seysoo.repository;

import sn.seysoo.domain.Localite;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Localite entity.
 */
@SuppressWarnings("unused")
public interface LocaliteRepository extends MongoRepository<Localite,String> {

}
