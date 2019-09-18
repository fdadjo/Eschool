package com.software.zone.pro.eschool.repository;

import com.software.zone.pro.eschool.domain.LessonHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LessonHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LessonHistoryRepository extends JpaRepository<LessonHistory, Long> {

}
