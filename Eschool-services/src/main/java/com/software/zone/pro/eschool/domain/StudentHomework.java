package com.software.zone.pro.eschool.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A StudentHomework.
 */
@Entity
@Table(name = "student_homework")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudentHomework extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "done")
    private Boolean done = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private User student;

    @ManyToOne
    @JoinColumn(name = "homework_id")
    @JsonIgnore
    private Homework homework;

    public StudentHomework() {
    }

    public StudentHomework(User student, Homework homework) {
        this.student = student;
        this.homework = homework;
    }

    public StudentHomework(User student, Homework homework, Boolean done) {
        this.student = student;
        this.homework = homework;
        this.done = done;
    }

    public StudentHomework(Long id, User student, Homework homework, Boolean done) {
        this.setId(id);
        this.student = student;
        this.homework = homework;
        this.done = done;
    }



    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDone() {
        return done;
    }

    public StudentHomework done(Boolean done) {
        this.done = done;
        return this;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public User getStudent() {
        return student;
    }

    public StudentHomework student(User user) {
        this.student = user;
        return this;
    }

    public void setStudent(User user) {
        this.student = user;
    }

    public Homework getHomework() {
        return homework;
    }

    public StudentHomework homework(Homework homework) {
        this.homework = homework;
        return this;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentHomework that = (StudentHomework) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(done, that.done) &&
            Objects.equals(student, that.student) &&
            Objects.equals(homework, that.homework);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, done, student, homework);
    }

    @Override
    public String toString() {
        return "StudentHomework{" +
            "id=" + id +
            ", done=" + done +
            ", student=" + student +
            ", homework=" + homework +
            '}';
    }
}
