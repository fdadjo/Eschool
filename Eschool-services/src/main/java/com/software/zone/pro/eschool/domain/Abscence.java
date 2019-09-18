package com.software.zone.pro.eschool.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.software.zone.pro.eschool.service.dto.abscence.AbscenceDTO;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Abscence.
 */
@Entity
@Table(name = "abscence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Abscence extends AbstractAuditingEntity implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "abscence")
    private Boolean abscence;

    @Column(name = "justify")
    private Boolean justify;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    @JsonIgnore
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    @JsonIgnore
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unity")
    @JsonIgnore
    private LessonHistory course;

    public Abscence() {
    }

    public Abscence(Long id, ZonedDateTime date, String commentaire, boolean abscence, boolean justify) {
        this.setId(id);
        this.date = date;
        this.commentaire = commentaire;
        this.abscence = abscence;
        this.justify = justify;
    }

    public Abscence(AbscenceDTO abscence) {
        setId(abscence.getId());
        this.date = abscence.getDate();
        this.commentaire = abscence.getCommentaire();
        this.abscence = abscence.isAbscence();
        this.justify = abscence.isJustify();
    }


    public void setJustify(boolean justify) {
        this.justify = justify;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LessonHistory getCourse() {
        return course;
    }

    public void setCourse(LessonHistory course) {
        this.course = course;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Abscence date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Abscence commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Boolean isAbscence() {
        return abscence;
    }

    public Abscence abscence(Boolean abscence) {
        this.abscence = abscence;
        return this;
    }

    public void setAbscence(Boolean abscence) {
        this.abscence = abscence;
    }

    public Boolean isJustify() {
        return justify;
    }

    public Abscence justify(Boolean justify) {
        this.justify = justify;
        return this;
    }

    public void setJustify(Boolean justify) {
        this.justify = justify;
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
        Abscence abscence = (Abscence) o;
        if (abscence.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), abscence.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Abscence{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", abscence='" + isAbscence() + "'" +
            ", justify='" + isJustify() + "'" +
            "}";
    }
}
