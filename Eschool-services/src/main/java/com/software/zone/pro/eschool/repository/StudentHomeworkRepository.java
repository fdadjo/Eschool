package com.software.zone.pro.eschool.repository;

import com.software.zone.pro.eschool.domain.StudentHomework;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the StudentHomework entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentHomeworkRepository extends JpaRepository<StudentHomework, Long> {

    @Query("select student_homework from StudentHomework student_homework where student_homework.student.login = ?#{principal.username}")
    List<StudentHomework> findByStudentIsCurrentUser();

}
