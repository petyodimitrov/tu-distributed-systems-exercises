package com.musala.samples.jhipster.microservices.gateway.repository;

import com.musala.samples.jhipster.microservices.gateway.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
