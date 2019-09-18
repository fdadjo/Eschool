package com.software.zone.pro.eschool.repository;

import com.software.zone.pro.eschool.domain.TimeSlotHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TimeSlotHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeSlotHistoryRepository extends JpaRepository<TimeSlotHistory, Long> {

}
