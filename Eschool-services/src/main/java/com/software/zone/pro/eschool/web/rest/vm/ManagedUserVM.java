package com.software.zone.pro.eschool.web.rest.vm;

import com.software.zone.pro.eschool.domain.User;
import com.software.zone.pro.eschool.service.dto.UserDTO;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    private String schoolName;

    private double ccCoef;

    private double cCoef;

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public ManagedUserVM(String login, String password, String firstName, String lastName,
                         String email, boolean activated, String langKey, Set<String> authorities, String cniNumber, String grade,
                         String matricule, String phone1) {
        super(login, firstName, lastName, email, activated, langKey, authorities, cniNumber, grade, matricule, phone1);


        this.password = password;
    }

    public ManagedUserVM(User user) {
        super(user);
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public double getCcCoef() { return ccCoef; }

    public void setCcCoef(double ccCoef) { this.ccCoef = ccCoef; }

    public double getcCoef() { return cCoef; }

    public void setcCoef(double cCoef) { this.cCoef = cCoef; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" +
            "} " + super.toString();
    }
}
