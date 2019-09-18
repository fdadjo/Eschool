package com.software.zone.pro.eschool.service.dto.school;


public class ClassroomCreateDto {

    private String classroomName;

    private Integer year;

    private Long schoolId;

    private Double tutionFees;

    private double ccCoef;

    private double cCoef;

    private Long professorId;

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

    public Double getTutionFees() {
        return tutionFees;
    }

    public void setTutionFees(Double tutionFees) {
        this.tutionFees = tutionFees;
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

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }
}
