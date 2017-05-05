package sn.seysoo.repository;

import sn.seysoo.domain.CarnetSante;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the CarnetSante entity.
 */
@SuppressWarnings("unused")
public interface CarnetSanteRepository extends MongoRepository<CarnetSante,String> {

}
