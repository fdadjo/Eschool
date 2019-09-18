package com.software.zone.pro.eschool.service.dto.user;

import com.software.zone.pro.eschool.security.AuthoritiesConstants;

import java.util.HashSet;
import java.util.Set;

public class StudentCreateDto extends UserCreateDto {

    private Set<String> schoolAuthorities = new HashSet<>();

    private Long classroomId;

    private Long schoolId;

    public StudentCreateDto() {
        super();
        this.schoolAuthorities.add(AuthoritiesConstants.STUDENT);
    }

    public StudentCreateDto(String login, String email, String password, String phone) {
        super(login, email, password, phone);
        this.schoolAuthorities.add(AuthoritiesConstants.STUDENT);
    }

    public StudentCreateDto(String login, String email, String password, String phone, Long classroomId, Long schoolId) {
        super(login, email, password, phone);
        this.classroomId = classroomId;
        this.schoolId = schoolId;
        this.schoolAuthorities.add(AuthoritiesConstants.STUDENT);
    }

    public Set<String> getSchoolAuthorities() {return schoolAuthorities;}

    void setSchoolAuthorities(Set<String> schoolAuthorities) {this.schoolAuthorities = schoolAuthorities;}

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public Long getSchoolId() {
        return schoolId;
    }
    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }
}
