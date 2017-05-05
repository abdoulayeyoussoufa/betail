package sn.seysoo.repository;

import sn.seysoo.domain.PartenaireBancaire;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the PartenaireBancaire entity.
 */
@SuppressWarnings("unused")
public interface PartenaireBancaireRepository extends MongoRepository<PartenaireBancaire,String> {

}
