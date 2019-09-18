package com.software.zone.pro.eschool.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.software.zone.pro.eschool.domain.enumeration.LessonPreparation;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A LessonHistoryPreparation.
 */
@Entity
@Table(name = "lesson_history_preparation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LessonHistoryPreparation extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_history_id")
    @JsonIgnore
    private LessonHistory lessonHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    @JsonIgnore
    private User teacher;

    @Column
    @Enumerated(EnumType.STRING)
    private LessonPreparation prepared = LessonPreparation.WAITING;

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public LessonPreparation getPrepared() {
        return prepared;
    }

    public void setPrepared(LessonPreparation prepared) {
        this.prepared = prepared;
    }


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LessonHistory getLessonHistory() {
        return lessonHistory;
    }

    public LessonHistoryPreparation lessonHistory(LessonHistory lessonHistory) {
        this.lessonHistory = lessonHistory;
        return this;
    }

    public void setLessonHistory(LessonHistory lessonHistory) {
        this.lessonHistory = lessonHistory;
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
        LessonHistoryPreparation lessonHistoryPreparation = (LessonHistoryPreparation) o;
        if (lessonHistoryPreparation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lessonHistoryPreparation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LessonHistoryPreparation{" +
            "id=" + id +
            ", lessonHistory=" + lessonHistory +
            ", teacher=" + teacher +
            ", prepared=" + prepared +
            '}';
    }
}
