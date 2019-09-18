package com.software.zone.pro.eschool.service.dto.lesson;

import com.software.zone.pro.eschool.domain.LessonHistory;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;

public class LessonHistoryDTO implements Serializable {

    private Long id;
    private double coef;
    private String courseName;
    private Long classroomId;
    private Long lessonId;
    private Instant createAt;
    private Instant endAt;
    private String createdBy;
    private boolean enabled = true;

    public LessonHistoryDTO() {
    }

    public LessonHistoryDTO(Long id, double coef, String courseName, Long classroomId, Long lessonId,
                            Instant createAt, Instant endAt, String createdBy, boolean enabled) {
        this.id = id;
        this.coef = coef;
        this.courseName = courseName;
        this.classroomId = classroomId;
        this.lessonId = lessonId;
        this.createAt = createAt;
        this.endAt = endAt;
        this.createdBy = createdBy;
        this.enabled = enabled;
    }

    public LessonHistoryDTO(LessonHistory lessonHistory) {
        this.id = lessonHistory.getId();
        this.coef = lessonHistory.getCoef();
        this.courseName = lessonHistory.getCourseName();
        this.classroomId = lessonHistory.getClassroom().getId();
        //this.lessoonId = lessonHistory.getLesson().getId();
        this.createAt = lessonHistory.getCreatedDate();
        this.endAt = lessonHistory.getLastModifiedDate();
        this.createdBy = lessonHistory.getCreatedBy();
        this.enabled = lessonHistory.isActivated();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Instant getEndAt() {
        return endAt;
    }

    public void setEndAt(Instant endAt) {
        this.endAt = endAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
