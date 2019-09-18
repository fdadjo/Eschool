package com.software.zone.pro.eschool.service.dto.school;

import com.software.zone.pro.eschool.domain.School;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;

public class SchoolCreateDto implements Serializable {

    private String schoolName;

    private Long directorId;

    private Long founderId;

    private Instant createdAt;

    public SchoolCreateDto(String schoolName) {
        this.schoolName = schoolName;
    }

    public SchoolCreateDto(School school) {
        this.schoolName = school.getSchoolName();
    }

    public SchoolCreateDto() {

    }

    public Long getFounderId() {
        return founderId;
    }

    public void setFounderId(Long founderId) {
        this.founderId = founderId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Long getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
