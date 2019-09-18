package com.software.zone.pro.eschool.service.impl;
import com.software.zone.pro.eschool.domain.Authority;
import com.software.zone.pro.eschool.domain.SchoolAuthority;
import com.software.zone.pro.eschool.domain.User;
import com.software.zone.pro.eschool.repository.AuthorityRepository;
import com.software.zone.pro.eschool.repository.SchoolAuthorityRepository;
import com.software.zone.pro.eschool.repository.SchoolRepository;
import com.software.zone.pro.eschool.security.SecurityUtils;
import com.software.zone.pro.eschool.service.dto.user.StudentCreateDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class  SchoolUserService {

    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private SchoolAuthorityRepository schoolAuthorityRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    void addSchoolUserRole(StudentCreateDto teacherCreateDto, User user) {
        teacherCreateDto.getSchoolAuthorities().forEach(schoolAuth -> {
            Authority authority = authorityRepository.findByName(schoolAuth);
            if (authority != null && teacherCreateDto.getSchoolId() != null) {
                SchoolAuthority schoolAuthority = new SchoolAuthority();
                schoolAuthority.setCreatedDate(Instant.now());
                schoolAuthority.setUser(user);
                schoolAuthority.setRole(authority);
                schoolAuthority.setSchool(schoolRepository.findById(teacherCreateDto.getSchoolId()).get());
                if(SecurityUtils.getCurrentUserLogin() != null) {
                    schoolAuthority.setCreatedBy(SecurityUtils.getCurrentUserLogin().toString());
                }
                schoolAuthorityRepository.save(schoolAuthority);
            }
        });
    }
}
