package com.software.zone.pro.eschool.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.software.zone.pro.eschool.service.dto.school.ClassroomDTO;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Classroom.
 */
@Entity
@Table(name = "classroom")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Classroom extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "classroom_name")
    private String classroomName;

    @Column(name = "jhi_year")
    private Integer year;

    @Column(name = "tution_fees")
    private Double tutionFees;

    @Column(name = "cc_coef")
    private Double ccCoef;

    @Column(name = "c_coef")
    private Double cCoef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    @JsonIgnore
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titular_id")
    @JsonIgnore
    private User professor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classroom", orphanRemoval = true)
    private List<ClassroomHistory> classroomHistories = new ArrayList<>();

    public Classroom(ClassroomDTO classroomDTO) {
        this.classroomName = classroomDTO.getClassroomName();
        this.year = classroomDTO.getYear();
        this.ccCoef = classroomDTO.getCcCoef();
        this.cCoef = classroomDTO.getcCoef();
    }

    public Classroom() {
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public User getProfessor() {
        return professor;
    }

    public void setProfessor(User professor) {
        this.professor = professor;
    }

    public List<ClassroomHistory> getClassroomHistories() {
        return classroomHistories;
    }

    public void setClassroomHistories(List<ClassroomHistory> classroomHistories) {
        this.classroomHistories = classroomHistories;
    }


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public Classroom classroomName(String classroomName) {
        this.classroomName = classroomName;
        return this;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public Integer getYear() {
        return year;
    }

    public Classroom year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getTutionFees() {
        return tutionFees;
    }

    public Classroom tutionFees(Double tutionFees) {
        this.tutionFees = tutionFees;
        return this;
    }

    public void setTutionFees(Double tutionFees) {
        this.tutionFees = tutionFees;
    }

    public Double getCcCoef() {
        return ccCoef;
    }

    public Classroom ccCoef(Double ccCoef) {
        this.ccCoef = ccCoef;
        return this;
    }

    public void setCcCoef(Double ccCoef) {
        this.ccCoef = ccCoef;
    }

    public Double getcCoef() {
        return cCoef;
    }

    public Classroom cCoef(Double cCoef) {
        this.cCoef = cCoef;
        return this;
    }

    public void setcCoef(Double cCoef) {
        this.cCoef = cCoef;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Classroom classroom = (Classroom) o;
        if (classroom.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classroom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Classroom{" +
            "id=" + getId() +
            ", classroomName='" + getClassroomName() + "'" +
            ", year=" + getYear() +
            ", tutionFees=" + getTutionFees() +
            ", ccCoef=" + getCcCoef() +
            ", cCoef=" + getcCoef() +
            "}";
    }
}
