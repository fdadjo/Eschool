package com.software.zone.pro.eschool.service.dto.user;

import com.software.zone.pro.eschool.domain.enumeration.UserType;
import com.software.zone.pro.eschool.security.AuthoritiesConstants;

import java.util.HashSet;
import java.util.Set;

public class FounderCreateDto extends StudentCreateDto {

    private String schoolName;

    public FounderCreateDto() {
        Set<String> auth = new HashSet<>();
        Set<String> schAuth = new HashSet<>();
        auth.add(AuthoritiesConstants.USER);
        schAuth.add(AuthoritiesConstants.FOUNDER);
        this.setAuthorities(auth);
        this.setSchoolAuthorities(schAuth);
    }

    public FounderCreateDto(String login, String email, String password, String phone, String schoolName) {
        super(login, email, password, phone);
        this.schoolName = schoolName;
        Set<String> auth = new HashSet<>();
        Set<String> schAuth = new HashSet<>();
        auth.add(AuthoritiesConstants.USER);
        schAuth.add(AuthoritiesConstants.FOUNDER);
        this.setAuthorities(auth);
        this.setSchoolAuthorities(schAuth);
        this.setUserType(UserType.FOUNDER);
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
