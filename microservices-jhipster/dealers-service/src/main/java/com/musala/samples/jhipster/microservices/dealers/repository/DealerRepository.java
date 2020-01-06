package com.musala.samples.jhipster.microservices.dealers.repository;

import com.musala.samples.jhipster.microservices.dealers.domain.Dealer;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Dealer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealerRepository extends MongoRepository<Dealer, String> {

}
