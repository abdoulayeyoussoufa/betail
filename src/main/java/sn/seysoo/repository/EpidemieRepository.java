package sn.seysoo.repository;

import sn.seysoo.domain.Epidemie;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Epidemie entity.
 */
@SuppressWarnings("unused")
public interface EpidemieRepository extends MongoRepository<Epidemie,String> {

}
