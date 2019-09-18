package com.software.zone.pro.eschool.service.dto.lesson;
import com.software.zone.pro.eschool.domain.Lesson;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Lesson entity.
 */
public class LessonDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private String tagKey;

    private Long categoryId;

    private boolean activate;

    public LessonDTO() {
    }

    public LessonDTO(Long id, String title, String description, String tagKey, Long categoryId, boolean activate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tagKey = tagKey;
        this.categoryId = categoryId;
        this.activate = activate;
    }

    public LessonDTO(Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.description = lesson.getDescription();
        this.tagKey = lesson.getTagKey();
        this.categoryId = lesson.getCategory().getId();
        this.activate = lesson.isActivated();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagKey() {
        return tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isActivate() { return activate; }

    public void setActivate(boolean activate) { this.activate = activate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LessonDTO lessonDTO = (LessonDTO) o;

        if ( ! Objects.equals(id, lessonDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LessonDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", tagKey='" + tagKey + '\'' +
            ", activate='" + tagKey + '\'' +
            ", categoryId=" + categoryId +
            '}';
    }
}
