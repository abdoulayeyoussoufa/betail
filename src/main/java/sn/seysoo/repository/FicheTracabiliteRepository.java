package sn.seysoo.repository;

import sn.seysoo.domain.FicheTracabilite;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the FicheTracabilite entity.
 */
@SuppressWarnings("unused")
public interface FicheTracabiliteRepository extends MongoRepository<FicheTracabilite,String> {

}
