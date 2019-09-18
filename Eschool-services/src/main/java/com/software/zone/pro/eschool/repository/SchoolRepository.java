package com.software.zone.pro.eschool.repository;

import com.software.zone.pro.eschool.domain.School;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the School entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    @Query(value = "SELECT s.* FROM school s " +
        " JOIN school_authority sa " +
        " ON sa.user_id = ?1 AND sa.school_id = s.id " +
        " JOIN(SELECT id AS usr_id, login AS director_name FROM jhi_user) AS jh_usr " +
        " ON s.director_id = jh_usr.usr_id OR s.founder_id = jh_usr.usr_id " +
        " WHERE s.activated = true ORDER BY s.id ASC", nativeQuery = true)
    List<School> getSchoolsByUserId(Long idUser);
}
