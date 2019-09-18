package com.software.zone.pro.eschool.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.software.zone.pro.eschool.domain.enumeration.ExamStatus;
import com.software.zone.pro.eschool.domain.enumeration.ExamType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Exam.
 */
@Entity
@Table(name = "exam")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Exam extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "coef")
    private Double coef;

    @Column(name = "type")
    private ExamType type;

    @Column(name = "exam_status")
    private ExamStatus status = ExamStatus.PROGRAMMATIC;

    @Column(name = "examination_file")
    private String examinationFile;

    @Column(name = "jhi_value")
    private Double value;

    @Column(name = "jhi_session")
    private String session;

    @Column(name = "planned_on")
    private ZonedDateTime plannedOn;@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_history_id")
    @JsonIgnore
    private LessonHistory lessonHistory;



    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Exam name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCoef() {
        return coef;
    }

    public Exam coef(Double coef) {
        this.coef = coef;
        return this;
    }

    public void setCoef(Double coef) {
        this.coef = coef;
    }

    public Double getValue() {
        return value;
    }

    public Exam value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getSession() {
        return session;
    }

    public Exam session(String session) {
        this.session = session;
        return this;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getExaminationFile() {
        return examinationFile;
    }

    public Exam examinationFile(String examinationFile) {
        this.examinationFile = examinationFile;
        return this;
    }

    public void setExaminationFile(String examinationFile) {
        this.examinationFile = examinationFile;
    }

    public ZonedDateTime getPlannedOn() {
        return plannedOn;
    }

    public Exam plannedOn(ZonedDateTime plannedOn) {
        this.plannedOn = plannedOn;
        return this;
    }

    public void setPlannedOn(ZonedDateTime plannedOn) {
        this.plannedOn = plannedOn;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public ExamType getType() {
        return type;
    }

    public void setType(ExamType type) {
        this.type = type;
    }

    public LessonHistory getLessonHistory() {
        return lessonHistory;
    }

    public void setLessonHistory(LessonHistory lessonHistory) {
        this.lessonHistory = lessonHistory;
    }

    public ExamStatus getStatus() { return status; }

    public void setStatus(ExamStatus status) { this.status = status; }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exam exam = (Exam) o;
        if (exam.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exam.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Exam{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", coef=" + getCoef() +
            ", value=" + getValue() +
            ", session='" + getSession() + "'" +
            ", examinationFile='" + getExaminationFile() + "'" +
            ", plannedOn='" + getPlannedOn() + "'" +
            "}";
    }
}
