package com.software.zone.pro.eschool.repository;

import com.software.zone.pro.eschool.domain.LessonHistoryPreparation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LessonHistoryPreparation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LessonHistoryPreparationRepository extends JpaRepository<LessonHistoryPreparation, Long> {

}
