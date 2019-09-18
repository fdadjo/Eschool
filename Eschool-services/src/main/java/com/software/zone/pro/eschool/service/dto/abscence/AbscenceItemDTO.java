package com.software.zone.pro.eschool.service.dto.abscence;

import com.software.zone.pro.eschool.domain.Abscence;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class AbscenceItemDTO extends AbscenceDTO implements Serializable {

    private String userName;
    private String schoolName;
    private String className;
    private String unityName;

    public AbscenceItemDTO() {
    }

    public AbscenceItemDTO(String userName, String schoolName, String className, String unityName) {
        this.userName = userName;
        this.schoolName = schoolName;
        this.className = className;
        this.unityName = unityName;
    }

    public AbscenceItemDTO(Long id, Long userId, Long schoolId, Long classId, Long unity, ZonedDateTime date,
                          String commentaire, boolean abscence, boolean justify) {

        super(id, userId, schoolId, classId, unity, date, commentaire, abscence, justify);
    }

    public AbscenceItemDTO(Long id, Long userId, Long schoolId, Long classId, Long unity, ZonedDateTime date,
                          String commentaire, boolean abscence, boolean justify, String userName, String schoolName, String className, String unityName) {

        super(id, userId, schoolId, classId, unity, date, commentaire, abscence, justify);

        this.userName = userName;
        this.schoolName = schoolName;
        this.className = className;
        this.unityName = unityName;
    }

    public AbscenceItemDTO(Abscence abscence) {
        super(abscence);
        this.userName = abscence.getUser().getLogin();
        this.schoolName = (abscence.getSchool() != null) ? abscence.getSchool().getSchoolName() : "";
        this.className = abscence.getClassroom().getClassroomName();
        this.unityName = (abscence.getCourse() != null) ? abscence.getCourse().getCourseName() : "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUnityName() {
        return unityName;
    }

    public void setUnityName(String unityName) {
        this.unityName = unityName;
    }
}
