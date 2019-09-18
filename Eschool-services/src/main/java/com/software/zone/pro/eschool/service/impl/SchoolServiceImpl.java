package com.software.zone.pro.eschool.service.impl;
import com.software.zone.pro.eschool.domain.School;
import com.software.zone.pro.eschool.domain.SchoolAuthority;
import com.software.zone.pro.eschool.domain.User;
import com.software.zone.pro.eschool.repository.AuthorityRepository;
import com.software.zone.pro.eschool.repository.SchoolAuthorityRepository;
import com.software.zone.pro.eschool.repository.SchoolRepository;
import com.software.zone.pro.eschool.repository.UserRepository;
import com.software.zone.pro.eschool.security.AuthoritiesConstants;
import com.software.zone.pro.eschool.security.SecurityUtils;
import com.software.zone.pro.eschool.service.SchoolService;
import com.software.zone.pro.eschool.service.UserService;
import com.software.zone.pro.eschool.service.dto.school.SchoolCreateDto;
import com.software.zone.pro.eschool.service.dto.school.SchoolDTO;
import com.software.zone.pro.eschool.service.dto.user.DirectorCreateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service Implementation for managing School.
 */
@Service
public class SchoolServiceImpl implements SchoolService {

    private final Logger log = LoggerFactory.getLogger(SchoolServiceImpl.class);

    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SchoolAuthorityRepository schoolAuthorityRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private UserService userService;

    /**
     * Save a school.
     *
     * @param schoolDTO the entity to save
     * @return the persisted entity
     */
    public School update(SchoolDTO schoolDTO) {
        log.debug("Request to update School : {}", schoolDTO);

        School school = new School(schoolDTO);
        if (schoolDTO.getId() != null){
            school.setClasses(schoolRepository.findById(schoolDTO.getId()).get().getClasses());
        }

        User user = userRepository.findById(schoolDTO.getFounderId()).get();
        school.setFounder(user);
        if (schoolDTO.getDirectorId() != null) {
            /*user = userRepository.findById(schoolDTO.getDirectorId());
            if (!user.isFounder(schoolDTO.getId())) {
                user.setUserType(UserType.DIRECTOR);
                user.getSchoolAuthorities().stream().map(
                        schoolAuthority -> {
                            schoolAuthority.getRole().setName(AuthoritiesConstants.DIRECTOR);
                            return schoolAuthority;
                        })
                    .collect(Collectors.toList());
                userService.updateUser(user);
            }
            school.setDirector(user);*/
        }
        return schoolRepository.save(school);
    }

    public School create(SchoolCreateDto schoolDTO) {
        log.debug("Request to create School : {}", schoolDTO);
        School school = new School();
        User director = null;

        User founder = userRepository.findById(schoolDTO.getFounderId()).get();
        school.setFounder(founder);
        school.setSchoolName(schoolDTO.getSchoolName().toString());
        if(SecurityUtils.getCurrentUserLogin() != null) {
            school.setCreatedBy(SecurityUtils.getCurrentUserLogin().toString());
        }
        if (schoolDTO.getDirectorId() != null) {
            director = userRepository.findById(schoolDTO.getDirectorId()).get();
            school.setDirector(director);
        } /*else {
            director = userService.createUser("d_" + director.getLogin(), director.getPassword(), director.getFirstName(),director.getLastName(),"d_" + director.getEmail(),director.getLangKey(), new String[] {AuthoritiesConstants.USER});
            school.setDirector(director);
        }*/

        school = schoolRepository.save(school);
        SchoolAuthority schoolAuthority = new SchoolAuthority();
        schoolAuthority = new SchoolAuthority();
        schoolAuthority.setUser(founder);
        schoolAuthority.setRole(authorityRepository.findByName(AuthoritiesConstants.FOUNDER));
        schoolAuthority.setSchool(school);
        if(SecurityUtils.getCurrentUserLogin() != null) {
            schoolAuthority.setCreatedBy(SecurityUtils.getCurrentUserLogin().toString());
        }
        schoolAuthorityRepository.save(schoolAuthority);

        if (schoolDTO.getDirectorId() != null) {
            schoolAuthority.setUser(director);
            schoolAuthority.setRole(authorityRepository.findByName(AuthoritiesConstants.DIRECTOR));
            schoolAuthority.setSchool(school);
            if(SecurityUtils.getCurrentUserLogin() != null) {
                schoolAuthority.setCreatedBy(SecurityUtils.getCurrentUserLogin().toString());
            }
            schoolAuthorityRepository.save(schoolAuthority);
        }

        return school;
    }

    @Override
    public SchoolDTO updateDirector(Long schoolId, DirectorCreateDto directorCreateDto) {
        School school = schoolRepository.findById(schoolId).get();
        User director = userService.createUser(directorCreateDto.getLogin(),directorCreateDto.getPassword(),directorCreateDto.getLogin(),directorCreateDto.getLogin(),directorCreateDto.getEmail(),"en", new String[] {AuthoritiesConstants.USER });
        school.setDirector(director);
        return new SchoolDTO(schoolRepository.save(school));
    }

    @Override
    public Page<School> findAll(Pageable pageable) {
        log.debug("Request to get all Schools");

        return schoolRepository.findAll(pageable);
    }

    @Override
    public List<School> getSchoolsByUserId(Long idUser) {
        log.debug("Request to get School by user : {}", idUser);

        Page<School> schoolsPage = null;
        List<School> schools = schoolRepository.getSchoolsByUserId(idUser);

        /*if (schools != null) {
            int start = (int) pageable.getOffset();
            int end = (start + pageable.getPageSize()) > schools.size() ? schools.size() : (start + pageable.getPageSize());
            schoolsPage = new PageImpl<>(schools.subList(start, end), pageable, schools.size());
        }*/

        return schools;
    }

}
