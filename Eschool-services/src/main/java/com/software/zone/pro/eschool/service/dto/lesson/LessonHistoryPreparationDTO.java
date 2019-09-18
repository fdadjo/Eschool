package com.software.zone.pro.eschool.service.dto.lesson;

import com.software.zone.pro.eschool.domain.LessonHistoryPreparation;
import com.software.zone.pro.eschool.domain.enumeration.LessonPreparation;

public class LessonHistoryPreparationDTO {

    private Long id;
    private Long lessonHistoryId;
    private String lessonHistoryName;
    private Long teacherId;
    private String teacherName;
    private LessonPreparation prepared;

    public LessonHistoryPreparationDTO() {
    }

    public LessonHistoryPreparationDTO(Long id, Long lessonHistoryId, Long teacherId, String teacherName, LessonPreparation prepared) {
        this.id = id;
        this.lessonHistoryId = lessonHistoryId;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.prepared = prepared;
    }

    public LessonHistoryPreparationDTO(LessonHistoryPreparation lessonHistoryPrep) {
        this.id = lessonHistoryPrep.getId();
        this.lessonHistoryId = lessonHistoryPrep.getLessonHistory().getId();
        this.lessonHistoryName = lessonHistoryPrep.getLessonHistory().getCourseName();
        this.teacherId = lessonHistoryPrep.getTeacher().getId();
        this.teacherName = lessonHistoryPrep.getTeacher().getLogin();
        this.prepared = lessonHistoryPrep.getPrepared();
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getLessonHistoryId() { return lessonHistoryId; }

    public void setLessonHistoryId(Long lessonHistoryId) { this.lessonHistoryId = lessonHistoryId; }

    public String getLessonHistoryName() { return lessonHistoryName; }

    public void setLessonHistoryName(String lessonHistoryName) { this.lessonHistoryName = lessonHistoryName; }

    public Long getTeacherId() { return teacherId; }

    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public LessonPreparation getPrepared() { return prepared; }

    public void setPrepared(LessonPreparation prepared) { this.prepared = prepared; }
}
