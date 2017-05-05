package sn.seysoo.repository;

import sn.seysoo.domain.VolMessage;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the VolMessage entity.
 */
@SuppressWarnings("unused")
public interface VolMessageRepository extends MongoRepository<VolMessage,String> {

}
