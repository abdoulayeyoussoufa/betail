package sn.seysoo.repository;

import sn.seysoo.domain.Race;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Race entity.
 */
@SuppressWarnings("unused")
public interface RaceRepository extends MongoRepository<Race,String> {

}
