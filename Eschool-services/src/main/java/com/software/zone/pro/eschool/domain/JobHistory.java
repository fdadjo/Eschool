package com.software.zone.pro.eschool.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A JobHistory.
 */
@Entity
@Table(name = "job_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JobHistory extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "monthly_salary")
    private Double monthlySalary;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    @JsonIgnore
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    @JsonIgnore
    private User worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_history_id")
    @JsonIgnore
    private LessonHistory lessonHistory;

    public JobHistory createdAt(Instant createdAt) {
        this.setCreatedDate(createdAt);
        return this;
    }

    public JobHistory endedAt(Instant endedAt) {
        this.setLastModifiedDate(endedAt);
        return this;
    }

    public Double getMonthlySalary() {
        return monthlySalary;
    }

    public JobHistory monthlySalary(Double monthlySalary) {
        this.monthlySalary = monthlySalary;
        return this;
    }

    public Job getJob() {
        return job;
    }

    public JobHistory job(Job job) {
        this.job = job;
        return this;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public User getWorker() {
        return worker;
    }

    public JobHistory worker(User user) {
        this.worker = user;
        return this;
    }

    public void setWorker(User user) {
        this.worker = user;
    }


    public void setMonthlySalary(Double monthlySalary) {
        this.monthlySalary = monthlySalary;
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
        JobHistory jobHistory = (JobHistory) o;
        if (jobHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobHistory{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedDate() + "'" +
            ", endedAt='" + getLastModifiedDate() + "'" +
            ", monthlySalary='" + monthlySalary + "'" +
            '}';
    }
}
