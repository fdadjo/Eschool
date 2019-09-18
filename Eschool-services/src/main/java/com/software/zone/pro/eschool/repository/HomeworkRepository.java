package com.software.zone.pro.eschool.repository;

import com.software.zone.pro.eschool.domain.Homework;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Homework entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {

}
