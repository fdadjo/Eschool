package com.software.zone.pro.eschool.service.dto.abscence;

import com.software.zone.pro.eschool.domain.Abscence;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class AbscenceDTO implements Serializable {

    private Long id;
    private Long userId;
    private Long schoolId;
    private Long classId;
    private Long unity;
    private ZonedDateTime date;
    private String commentaire;
    private boolean abscence = true;
    private boolean justify = false;

    public AbscenceDTO() {
    }

    public AbscenceDTO(Long id, Long userId, Long schoolId, Long classId, Long unity, ZonedDateTime date, String commentaire, boolean abscence, boolean justify) {
        this.id = id;
        this.userId = userId;
        this.schoolId = schoolId;
        this.classId = classId;
        this.unity = unity;
        this.date = date;
        this.commentaire = commentaire;
        this.abscence = abscence;
        this.justify = justify;
    }

    public AbscenceDTO(Abscence abscence) {
        this.id = abscence.getId();
        this.userId = abscence.getUser().getId();
        this.schoolId = abscence.getSchool().getId();
        this.classId = abscence.getClassroom().getId();
        if (abscence.getCourse() != null) {
            this.unity = abscence.getCourse().getId();
        }
        this.date = abscence.getDate();
        this.commentaire = abscence.getCommentaire();
        this.abscence = abscence.isAbscence();
        this.justify = abscence.isJustify();
    }

    public AbscenceDTO(AbscenceItemDTO abscenceItemDTO) {
        this.id = abscenceItemDTO.getId();
        this.userId = abscenceItemDTO.getUserId();
        this.schoolId = abscenceItemDTO.getSchoolId();
        this.classId = abscenceItemDTO.getClassId();
        this.unity = abscenceItemDTO.getUnity();
        this.date = abscenceItemDTO.getDate();
        this.commentaire = abscenceItemDTO.getCommentaire();
        this.abscence = abscenceItemDTO.isAbscence();
        this.justify = abscenceItemDTO.isJustify();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getUnity() { return unity; }

    public void setUnity(Long unity) { this.unity = unity; }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public boolean isAbscence() {
        return abscence;
    }

    public void setAbscence(boolean abscence) {
        this.abscence = abscence;
    }

    public boolean isJustify() {
        return justify;
    }

    public void setJustify(boolean justify) {
        this.justify = justify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbscenceDTO that = (AbscenceDTO) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AbscenceDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", schoolId=" + schoolId +
                ", classId=" + classId +
                ", unity=" + unity +
                ", date=" + date +
                ", commentaire='" + commentaire + '\'' +
                ", abscence=" + abscence +
                ", justify=" + justify +
                '}';
    }
}
