package com.software.zone.pro.eschool.domain;

import com.software.zone.pro.eschool.config.Constants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.software.zone.pro.eschool.domain.enumeration.UserType;
import com.software.zone.pro.eschool.security.AuthoritiesConstants;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.*;
import java.time.Instant;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @Column
    private UserType userType = UserType.FOUNDER;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 6)
    @Column(name = "lang_key", length = 6)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private ZonedDateTime resetDate = null;

    @Column(name = "birth_day")
    private ZonedDateTime birthday;
    @Column(name = "place_of_birth")
    private String placeOfBirth;
    @Column(name = "phone_1")
    private String phone1;
    @Column(name = "phone_2")
    private String phone2;
    @Column(name = "last_password_reset")
    private Date lastPasswordResetDate;
    @Column(name = "year_recruitment")
    private ZonedDateTime yearRecruitment;
    @Column(name = "matricule")
    private String matricule;
    @Column(name = "cni_number")
    private String cniNumber;

    /// Student data

    @Column(name = "father_name")
    private String fatherName;
    @Column(name = "mother_name")
    private String motherName;

    /// Teacher Data
    @Column(name = "diploma")
    private String diploma;
    @Column(name = "grade")
    private String grade;
    @Column(name = "diploma_number")
    private Integer diplomaNumber;
    @Column(name = "gross_salary")
    private Double grossSalary;
    @Column(name = "hourly_salary")
    private Double hourlySalary;
    @Column(name = "net_salary")
    private Double netSalary;
    @Column(name = "year_graduation")
    private ZonedDateTime yearGraduation;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private Set<SchoolAuthority> schoolAuthorities = new HashSet<>();

    public Boolean isAdmin() {
        return getAuthorities().stream().anyMatch(authority -> {
            return authority.equalsAuthority(new Authority(AuthoritiesConstants.ADMIN));
        });
    }

    public Boolean isFounder(Long idSchool) {
        return findSchoolRole(idSchool, AuthoritiesConstants.FOUNDER);
    }

    public Boolean isDirector(Long idSchool) {
        return findSchoolRole(idSchool,AuthoritiesConstants.DIRECTOR);
    }

    public Boolean isStudent(Long idSchool) {
        return findSchoolRole(idSchool,AuthoritiesConstants.STUDENT);
    }

    public Boolean isTeacher(Long idSchool) {
        return findSchoolRole(idSchool,AuthoritiesConstants.TEACHER);
    }

    private Boolean findSchoolRole(Long idSchool, String role) {
        return getSchoolAuthorities().stream().anyMatch(schoolAuthority -> {
            return schoolAuthority.isActivated() && schoolAuthority.getRole().equalsAuthority(new Authority(role))
                && schoolAuthority.getSchool().getId().equals(idSchool);
        });
    }











    public UserType getUserType() { return userType; }

    public void setUserType(UserType userType) { this.userType = userType; }

    public boolean isEnabled() {
        return isActivated();
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public ZonedDateTime getYearRecruitment() {
        return yearRecruitment;
    }

    public void setYearRecruitment(ZonedDateTime yearRecruitment) {
        this.yearRecruitment = yearRecruitment;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getCniNumber() {
        return cniNumber;
    }

    public void setCniNumber(String cniNumber) {
        this.cniNumber = cniNumber;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getDiplomaNumber() {
        return diplomaNumber;
    }

    public void setDiplomaNumber(Integer diplomaNumber) {
        this.diplomaNumber = diplomaNumber;
    }

    public Double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(Double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public Double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Double netSalary) {
        this.netSalary = netSalary;
    }

    public ZonedDateTime getYearGraduation() {
        return yearGraduation;
    }

    public void setYearGraduation(ZonedDateTime yearGraduation) {
        this.yearGraduation = yearGraduation;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    // Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public ZonedDateTime getResetDate() {
        return resetDate;
    }

    public void setResetDate(ZonedDateTime resetDate) {
        this.resetDate = resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<SchoolAuthority> getSchoolAuthorities() {
        return schoolAuthorities;
    }

    public void setSchoolAuthorities(Set<SchoolAuthority> schoolAuthorities) {
        this.schoolAuthorities = schoolAuthorities;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;
        return !(user.getId() == null || getId() == null) && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            "}";
    }
}
