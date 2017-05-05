package sn.seysoo.repository;

import sn.seysoo.domain.Animal;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Animal entity.
 */
@SuppressWarnings("unused")
public interface AnimalRepository extends MongoRepository<Animal,String> {

}
