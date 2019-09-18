package com.software.zone.pro.eschool.domain;


import com.software.zone.pro.eschool.service.dto.school.SchoolDTO;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A School.
 */
@Entity
@Table(name = "school")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class School extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "devise")
    private String devise;

    @Column(name = "description")
    private String description;

    @Column(name = "cc_coef")
    private Double ccCoef;

    @Column(name = "c_coef")
    private Double cCoef;

    @Column(name = "logo_link")
    private String logoLink;

    @Column(name = "countryname")
    private String countryname;

    @Column(name = "country_motto")
    private String countryMotto;

    @Column(name = "country_secretary")
    private String countrySecretary;

    @Column(name = "country_sub_secretary")
    private String countrySubSecretary;

    @Column(name = "school_motto")
    private String schoolMotto;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "school", orphanRemoval = true)
    private Set<Classroom> classes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "director_id")
    private User director;

    @ManyToOne
    @JoinColumn(name = "founder_id", nullable = false)
    private User founder;

    public School() {
    }

    public School(String schoolName, String streetAddress, String postalCode, String city, String stateProvince, String telephone, Instant createdDate, String devise, String description) {
        this.schoolName     = schoolName;
        this.streetAddress  = streetAddress;
        this.postalCode     = postalCode;
        this.city           = city;
        this.stateProvince  = stateProvince;
        this.telephone      = telephone;
        this.setCreatedDate(createdDate);
        this.devise         = devise;
        this.description    = description;
    }

    public School(SchoolDTO schoolDTO) {
        this.setId(schoolDTO.getId());
        this.schoolName     = schoolDTO.getSchoolName();
        this.streetAddress  = schoolDTO.getStreetAddress();
        this.postalCode     = schoolDTO.getPostalCode();
        this.city           = schoolDTO.getCity();
        this.stateProvince  = schoolDTO.getStateProvince();
        this.telephone      = schoolDTO.getTelephone();
        this.setCreatedDate(schoolDTO.getCreatedAt());
        this.devise         = schoolDTO.getDevise();
        this.description    = schoolDTO.getDescription();
        this.ccCoef         = schoolDTO.getCcCoef();
        this.cCoef          = schoolDTO.getcCoef();
        this.logoLink       = schoolDTO.getLogoLink();
        this.countryname         = schoolDTO.getCountryName();
        this.countryMotto        = schoolDTO.getCountryMotto();
        this.countrySecretary    = schoolDTO.getCountrySecretary();
        this.countrySubSecretary = schoolDTO.getCountrySubSecretary();
        this.schoolMotto         = schoolDTO.getSchoolMotto();
    }



    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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

    public double getCcCoef() {
        return ccCoef;
    }

    public void setCcCoef(double ccCoef) {
        this.ccCoef = ccCoef;
    }

    public double getcCoef() {
        return cCoef;
    }

    public void setcCoef(double cCoef) {
        this.cCoef = cCoef;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public void setLogoLink(String logoLink) {
        this.logoLink = logoLink;
    }

    public Set<Classroom> getClasses() {
        return classes;
    }

    public void setClasses(Set<Classroom> classes) {
        this.classes = classes;
    }

    public User getDirector() {
        return director;
    }

    public void setDirector(User director) {
        this.director = director;
    }

    public User getFounder() {
        return founder;
    }

    public void setFounder(User founder) {
        this.founder = founder;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public School schoolName(String schoolName) {
        this.schoolName = schoolName;
        return this;
    }

    public School stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }
    public School telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }
    public School devise(String devise) {
        this.devise = devise;
        return this;
    }
    public void setCcCoef(Double ccCoef) {
        this.ccCoef = ccCoef;
    }

    public School cCoef(Double cCoef) {
        this.cCoef = cCoef;
        return this;
    }

    public void setcCoef(Double cCoef) {
        this.cCoef = cCoef;
    }

    public String getCountryname() {
        return countryname;
    }

    public School countryname(String countryname) {
        this.countryname = countryname;
        return this;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getCountryMotto() {
        return countryMotto;
    }

    public School countryMotto(String countryMotto) {
        this.countryMotto = countryMotto;
        return this;
    }

    public void setCountryMotto(String countryMotto) {
        this.countryMotto = countryMotto;
    }

    public String getCountrySecretary() {
        return countrySecretary;
    }

    public School countrySecretary(String countrySecretary) {
        this.countrySecretary = countrySecretary;
        return this;
    }

    public void setCountrySecretary(String countrySecretary) {
        this.countrySecretary = countrySecretary;
    }

    public String getCountrySubSecretary() {
        return countrySubSecretary;
    }

    public School countrySubSecretary(String countrySubSecretary) {
        this.countrySubSecretary = countrySubSecretary;
        return this;
    }

    public void setCountrySubSecretary(String countrySubSecretary) {
        this.countrySubSecretary = countrySubSecretary;
    }

    public String getSchoolMotto() {
        return schoolMotto;
    }

    public School schoolMotto(String schoolMotto) {
        this.schoolMotto = schoolMotto;
        return this;
    }

    public void setSchoolMotto(String schoolMotto) {
        this.schoolMotto = schoolMotto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        School school = (School) o;
        if (school.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), school.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "School{" +
            "id=" + getId() +
            ", schoolName='" + getSchoolName() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", devise='" + getDevise() + "'" +
            ", description='" + getDescription() + "'" +
            ", ccCoef=" + getCcCoef() +
            ", cCoef=" + getcCoef() +
            ", logoLink='" + getLogoLink() + "'" +
            ", countryname='" + getCountryname() + "'" +
            ", countryMotto='" + getCountryMotto() + "'" +
            ", countrySecretary='" + getCountrySecretary() + "'" +
            ", countrySubSecretary='" + getCountrySubSecretary() + "'" +
            ", schoolMotto='" + getSchoolMotto() + "'" +
            "}";
    }
}
