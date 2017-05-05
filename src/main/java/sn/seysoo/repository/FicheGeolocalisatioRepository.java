package sn.seysoo.repository;

import sn.seysoo.domain.FicheGeolocalisatio;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the FicheGeolocalisatio entity.
 */
@SuppressWarnings("unused")
public interface FicheGeolocalisatioRepository extends MongoRepository<FicheGeolocalisatio,String> {

}
