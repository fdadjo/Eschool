package com.software.zone.pro.eschool.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TimeSlotHistory.
 */
@Entity
@Table(name = "time_slot_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TimeSlotHistory  {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timesheet_id")
    @JsonIgnore
    private TimeSheet timeSheet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    @JsonIgnore
    private User professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id")
    @JsonIgnore
    private TimeSlot slot;

    public TimeSlotHistory() {
    }

    public TimeSlotHistory(TimeSheet timeSheet, User professor, TimeSlot slot) {
        this.timeSheet = timeSheet;
        this.professor = professor;
        this.slot = slot;
    }


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeSheet getTimesheet() {
        return timeSheet;
    }

    public TimeSlotHistory timesheet(TimeSheet timesheet) {
        this.timeSheet = timesheet;
        return this;
    }

    public void setTimesheet(TimeSheet timesheet) {
        this.timeSheet = timesheet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlotHistory that = (TimeSlotHistory) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(timeSheet, that.timeSheet) &&
            Objects.equals(professor, that.professor) &&
            Objects.equals(slot, that.slot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeSheet, professor, slot);
    }

    @Override
    public String toString() {
        return "TimeSlotHistory{" +
            "id=" + id +
            ", timeSheet=" + timeSheet +
            ", professor=" + professor +
            ", slot=" + slot +
            '}';
    }
}
