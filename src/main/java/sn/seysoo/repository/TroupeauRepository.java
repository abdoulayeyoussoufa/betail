package sn.seysoo.repository;

import sn.seysoo.domain.Troupeau;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Troupeau entity.
 */
@SuppressWarnings("unused")
public interface TroupeauRepository extends MongoRepository<Troupeau,String> {

}
