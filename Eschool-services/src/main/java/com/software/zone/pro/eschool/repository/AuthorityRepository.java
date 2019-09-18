package com.software.zone.pro.eschool.repository;

import com.software.zone.pro.eschool.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    Authority findByName(String name);
}
