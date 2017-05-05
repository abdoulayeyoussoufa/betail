package sn.seysoo.repository;

import sn.seysoo.domain.Police;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Police entity.
 */
@SuppressWarnings("unused")
public interface PoliceRepository extends MongoRepository<Police,String> {

}
