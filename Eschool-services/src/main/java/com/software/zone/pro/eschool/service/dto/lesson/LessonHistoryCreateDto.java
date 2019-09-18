package com.software.zone.pro.eschool.service.dto.lesson;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public class LessonHistoryCreateDto {

    @NotNull
    private double coef;
    @NotNull
    private String courseName;
    @NotNull
    private Long classroomId;
    @NotNull
    private Long lessonId;

    public double getCoef() {
        return coef;
    }

    public void setCoef(double coef) {
        this.coef = coef;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }
}
