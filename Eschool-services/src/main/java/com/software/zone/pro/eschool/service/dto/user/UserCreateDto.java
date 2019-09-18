package com.software.zone.pro.eschool.service.dto.user;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.software.zone.pro.eschool.config.Constants;
import com.software.zone.pro.eschool.domain.enumeration.UserType;
import com.software.zone.pro.eschool.security.AuthoritiesConstants;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TeacherCreateDto.class, name = "teacher"),
        @JsonSubTypes.Type(value = StudentCreateDto.class, name = "student"),
        @JsonSubTypes.Type(value = DirectorCreateDto.class, name = "director"),
        @JsonSubTypes.Type(value = FounderCreateDto.class, name = "founder"),
})
public abstract class UserCreateDto {

    private Set<String> authorities = new HashSet<>();

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 5, max = 100)
    private String login;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 100)
    private String phone;

    private String type = "founder";

    private UserType userType = UserType.STUDENT;

    UserCreateDto() {
        this.authorities.add(AuthoritiesConstants.USER);
    }

    UserCreateDto(String login, String email, String password, String phone) {
        this();
        this.login = login;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserType getUserType() {
        return userType;
    }

    void setUserType(UserType userType) {
        this.userType = userType;
    }
}
