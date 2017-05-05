package sn.seysoo.repository;

import sn.seysoo.domain.Accessoire;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Accessoire entity.
 */
@SuppressWarnings("unused")
public interface AccessoireRepository extends MongoRepository<Accessoire,String> {

}
