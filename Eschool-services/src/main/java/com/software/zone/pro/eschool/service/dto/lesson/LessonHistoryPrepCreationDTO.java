package com.software.zone.pro.eschool.service.dto.lesson;

import java.util.ArrayList;
import java.util.List;


public class LessonHistoryPrepCreationDTO {

    private List<LessonHistoryPreparationDTO> listItems =  new ArrayList<>();

    public LessonHistoryPrepCreationDTO() {
    }

    public List<LessonHistoryPreparationDTO> getListItems() { return listItems; }

    public void setListItems(List<LessonHistoryPreparationDTO> listItems) { this.listItems = listItems; }

    public void addItem(LessonHistoryPreparationDTO lessonHistoryPrepDTO) { this.listItems.add(lessonHistoryPrepDTO); }
}
