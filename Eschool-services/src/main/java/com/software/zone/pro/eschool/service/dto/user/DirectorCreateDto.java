package com.software.zone.pro.eschool.service.dto.user;

import com.software.zone.pro.eschool.domain.enumeration.UserType;
import com.software.zone.pro.eschool.security.AuthoritiesConstants;

import java.util.HashSet;
import java.util.Set;

public class DirectorCreateDto extends StudentCreateDto {

    public DirectorCreateDto() {
    }

    public DirectorCreateDto(String login, String email, String password, String phone) {
        super(login, email, password, phone);

        Set<String> auth = new HashSet<>();
        auth.add(AuthoritiesConstants.USER);
        this.setAuthorities(auth);
        Set<String> schoolAuth = new HashSet<>();
        schoolAuth.add(AuthoritiesConstants.DIRECTOR);
        this.setSchoolAuthorities(schoolAuth);
        this.setUserType(UserType.DIRECTOR);
    }

}
