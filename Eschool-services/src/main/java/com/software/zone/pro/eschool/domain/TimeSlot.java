package com.software.zone.pro.eschool.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TimeSlot.
 */
@Entity
@Table(name = "time_slot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TimeSlot extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "key_name")
    private String keyName;

    @Column(name = "day_of_week")
    private DayOfWeek weekDay;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    @JsonIgnore
    private Classroom place;


    public TimeSlot startAt(Instant startAt) {
        this.setCreatedDate(startAt);
        return this;
    }

    public TimeSlot endAt(Instant endAt) {
        this.setCreatedDate(endAt);
        return this;
    }


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public TimeSlot keyName(String keyName) {
        this.keyName = keyName;
        return this;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return Objects.equals(id, timeSlot.id) &&
            Objects.equals(keyName, timeSlot.keyName) &&
            weekDay == timeSlot.weekDay &&
            Objects.equals(place, timeSlot.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyName, weekDay, place);
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
            "id=" + id +
            ", keyName='" + keyName + '\'' +
            ", weekDay=" + weekDay +
            ", place=" + place +
            '}';
    }
}
