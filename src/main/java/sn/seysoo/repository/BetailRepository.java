package sn.seysoo.repository;

import sn.seysoo.domain.Betail;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Betail entity.
 */
@SuppressWarnings("unused")
public interface BetailRepository extends MongoRepository<Betail,String> {

}
