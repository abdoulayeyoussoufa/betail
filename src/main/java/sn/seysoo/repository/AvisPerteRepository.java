package sn.seysoo.repository;

import sn.seysoo.domain.AvisPerte;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the AvisPerte entity.
 */
@SuppressWarnings("unused")
public interface AvisPerteRepository extends MongoRepository<AvisPerte,String> {

}
