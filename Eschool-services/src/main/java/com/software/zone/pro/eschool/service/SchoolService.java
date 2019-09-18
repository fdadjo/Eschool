package com.software.zone.pro.eschool.service;

import com.software.zone.pro.eschool.domain.School;
import com.software.zone.pro.eschool.service.dto.school.SchoolCreateDto;
import com.software.zone.pro.eschool.service.dto.school.SchoolDTO;
import com.software.zone.pro.eschool.service.dto.user.DirectorCreateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface SchoolService {

    School update(SchoolDTO school);

    School create(SchoolCreateDto schoolDTO);

    SchoolDTO updateDirector(Long schoolId, DirectorCreateDto directorCreateDto);

    Page<School> findAll(Pageable pageable);

    List<School> getSchoolsByUserId(Long idUser);
}
