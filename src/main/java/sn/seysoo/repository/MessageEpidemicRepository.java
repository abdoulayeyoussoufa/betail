package sn.seysoo.repository;

import sn.seysoo.domain.MessageEpidemic;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the MessageEpidemic entity.
 */
@SuppressWarnings("unused")
public interface MessageEpidemicRepository extends MongoRepository<MessageEpidemic,String> {

}
