package com.software.zone.pro.eschool.service.dto.school;
import com.software.zone.pro.eschool.domain.School;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the School entity.
 */
public class SchoolDTO extends SchoolCreateDto implements Serializable {

    private Long id;

    private String streetAddress;

    private String postalCode;

    private String city;

    private String stateProvince;

    private String telephone;

    private String devise;

    private String description;

    private String countryName;

    private String countryMotto;

    private String countrySecretary;

    private String countrySubSecretary;

    private String schoolMotto;

    private String logoLink;

    private boolean activate;

    private double ccCoef;

    private double cCoef;

    public SchoolDTO() {
        super();
    }

    public SchoolDTO(String schoolName) {
        super(schoolName);
    }

    public SchoolDTO(School school) {
        super(school.getSchoolName());
        this.id = school.getId();
        this.streetAddress  = school.getStreetAddress();
        this.postalCode     = school.getPostalCode();
        this.city           = school.getCity();
        this.stateProvince  = school.getStateProvince();
        this.telephone      = school.getTelephone();
        this.setCreatedAt(school.getCreatedDate());
        this.devise         = school.getDevise();
        this.description    = school.getDescription();
        this.ccCoef         = school.getCcCoef();
        this.cCoef          = school.getcCoef();
        this.logoLink       = school.getLogoLink();
        this.countryName         = school.getCountryname();
        this.countryMotto        = school.getCountryMotto();
        this.countrySecretary    = school.getCountrySecretary();
        this.countrySubSecretary = school.getCountrySubSecretary();
        this.schoolMotto         = school.getSchoolMotto();
        if (school.getDirector() != null) {
            this.setDirectorId(school.getDirector().getId());
        }
        if (school.getFounder() != null) {
            this.setFounderId(school.getFounder().getId());
        }

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }
    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }



    public String getDevise() {
        return devise;
    }
    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActivate() { return activate; }
    public void setActivate(boolean activate) { this.activate = activate; }

    public double getCcCoef() { return ccCoef; }
    public void setCcCoef(double ccCoef) { this.ccCoef = ccCoef; }

    public double getcCoef() { return cCoef; }
    public void setcCoef(double cCoef) { this.cCoef = cCoef; }

    public String getLogoLink() { return logoLink; }
    public void setLogoLink(String logoLink) { this.logoLink = logoLink; }

    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }

    public String getCountryMotto() { return countryMotto; }
    public void setCountryMotto(String countryMotto) { this.countryMotto = countryMotto; }

    public String getCountrySecretary() { return countrySecretary; }
    public void setCountrySecretary(String countrySecretary) { this.countrySecretary = countrySecretary; }

    public String getCountrySubSecretary() { return countrySubSecretary; }
    public void setCountrySubSecretary(String countrySubSecretary) { this.countrySubSecretary = countrySubSecretary; }

    public String getSchoolMotto() { return schoolMotto; }
    public void setSchoolMotto(String schoolMotto) { this.schoolMotto = schoolMotto; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SchoolDTO schoolDTO = (SchoolDTO) o;

        if ( ! Objects.equals(id, schoolDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SchoolDTO{" +
                "id=" + id +
                ", schoolName='" + getSchoolName()+ "'" +
                ", directorId='" + getDirectorId() + "'" +
                ", streetAddress='" + streetAddress + "'" +
                ", postalCode='" + postalCode + "'" +
                ", city='" + city + "'" +
                ", stateProvince='" + stateProvince + "'" +
                ", telephone='" + telephone + "'" +
                ", ccCoef='" + ccCoef + "'" +
                ", cCoef='" + cCoef + "'" +
                ", createdAt='" + getCreatedAt() + "'" +
                ", devise='" + devise + "'" +
                ", description='" + description + "'" +
                ", activate='" + activate + "'" +
                '}';
    }
}
