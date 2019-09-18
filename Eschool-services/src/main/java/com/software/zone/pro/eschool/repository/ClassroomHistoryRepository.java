package com.software.zone.pro.eschool.repository;

import com.software.zone.pro.eschool.domain.ClassroomHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClassroomHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassroomHistoryRepository extends JpaRepository<ClassroomHistory, Long> {

}
