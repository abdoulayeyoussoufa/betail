package sn.seysoo.repository;

import sn.seysoo.domain.Veterinaire;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Veterinaire entity.
 */
@SuppressWarnings("unused")
public interface VeterinaireRepository extends MongoRepository<Veterinaire,String> {

}
