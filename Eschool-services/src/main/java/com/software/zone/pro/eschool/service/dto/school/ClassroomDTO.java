package com.software.zone.pro.eschool.service.dto.school;

import com.software.zone.pro.eschool.domain.Classroom;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Classroom entity.
 */
public class ClassroomDTO implements Serializable {

    private Long id;

    private Long professorId;

    private Integer studentNum = 0;

    private Instant createdAt;

    private ZonedDateTime endedAt;

    private String classroomName;

    private Integer year;

    private Long schoolId;

    private Double fees;

    private double ccCoef;

    private double cCoef;

    private boolean activate;

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public ClassroomDTO() {

    }

    public ClassroomDTO(Long id, Instant createdAt, String classroomName, Integer year, Long schoolId) {
        this.id             = id;
        this.createdAt      = createdAt;
        this.classroomName  = classroomName;
        this.year           = year;
        this.schoolId       = schoolId;
    }

    public ClassroomDTO(Classroom classroom) {
        this.id             = classroom.getId();
        this.createdAt      = classroom.getCreatedDate();
        this.classroomName  = classroom.getClassroomName();
        this.year           = classroom.getYear();
        this.schoolId       = classroom.getSchool().getId();
        this.ccCoef         = classroom.getCcCoef();
        this.cCoef          = classroom.getcCoef();
        this.professorId    = classroom.getProfessor().getId();
        this.fees           = classroom.getTutionFees();
        this.activate       = classroom.isActivated();
        this.studentNum     = classroom.getClassroomHistories().size();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfessorId() { return professorId; }

    public void setProfessorId(Long professorId) { this.professorId = professorId; }

    public Integer getStudentNum() { return studentNum; }

    public void setStudentNum(Integer studentNum) { this.studentNum = studentNum; }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(ZonedDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public double getCcCoef() {
        return ccCoef;
    }

    public void setCcCoef(double ccCoef) {
        this.ccCoef = ccCoef;
    }

    public double getcCoef() {
        return cCoef;
    }

    public void setcCoef(double cCoef) {
        this.cCoef = cCoef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassroomDTO classroomDTO = (ClassroomDTO) o;

        if ( ! Objects.equals(id, classroomDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClassroomDTO{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", endedAt=" + endedAt +
                ", classroomName='" + classroomName + '\'' +
                ", year=" + year +
                ", schoolId=" + schoolId +
                ", fees=" + fees +
                ", activate=" + activate +
                '}';
    }
}
