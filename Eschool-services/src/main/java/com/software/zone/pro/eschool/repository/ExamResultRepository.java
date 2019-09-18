package com.software.zone.pro.eschool.repository;

import com.software.zone.pro.eschool.domain.ExamResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExamResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

}
