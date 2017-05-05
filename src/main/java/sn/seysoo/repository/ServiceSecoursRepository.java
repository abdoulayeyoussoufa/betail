package sn.seysoo.repository;

import sn.seysoo.domain.ServiceSecours;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ServiceSecours entity.
 */
@SuppressWarnings("unused")
public interface ServiceSecoursRepository extends MongoRepository<ServiceSecours,String> {

}
