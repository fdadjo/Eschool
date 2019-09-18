package com.software.zone.pro.eschool.repository;

import com.software.zone.pro.eschool.domain.SchoolAuthority;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the SchoolAuthority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchoolAuthorityRepository extends JpaRepository<SchoolAuthority, Long> {

    @Query("select school_authority from SchoolAuthority school_authority where school_authority.user.login = ?#{principal.username}")
    List<SchoolAuthority> findByUserIsCurrentUser();

}
