package com.software.zone.pro.eschool.service.dto.lesson;


import com.software.zone.pro.eschool.domain.Lesson;

public class LessonFullDTO extends LessonDTO {

    private String categoryName;

    public LessonFullDTO(Lesson lesson) {
        super(lesson);
        this.categoryName = lesson.getCategory().getName();
    }

    public String getCategoryName() { return categoryName; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
