package sn.seysoo.repository;

import sn.seysoo.domain.FicheAnimal;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the FicheAnimal entity.
 */
@SuppressWarnings("unused")
public interface FicheAnimalRepository extends MongoRepository<FicheAnimal,String> {

}
