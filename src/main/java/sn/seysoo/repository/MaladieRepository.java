package sn.seysoo.repository;

import sn.seysoo.domain.Maladie;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Maladie entity.
 */
@SuppressWarnings("unused")
public interface MaladieRepository extends MongoRepository<Maladie,String> {

}
