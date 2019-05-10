package io.chaka.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ChakaUser.
 */
@Entity
@Table(name = "chaka_user")
public class ChakaUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthday")
    private Instant birthday;

    @Column(name = "username")
    private String username;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "zip")
    private String zip;

    @Column(name = "street")
    private String street;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "language")
    private String language;

    @Column(name = "data_of_registration")
    private Instant dataOfRegistration;

    @OneToOne
    @JoinColumn(unique = true)
    private UserAccount userID;

    @OneToMany(mappedBy = "chakaUser")
    private Set<Transaction> userIDS = new HashSet<>();

    @OneToMany(mappedBy = "chakaUser")
    private Set<Invitation> userIDS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public ChakaUser userID(String userID) {
        this.userID = userID;
        return this;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public ChakaUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public ChakaUser surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public ChakaUser birthday(Instant birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public ChakaUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public ChakaUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public ChakaUser country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public ChakaUser city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public ChakaUser zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public ChakaUser street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getEmail() {
        return email;
    }

    public ChakaUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public ChakaUser phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLanguage() {
        return language;
    }

    public ChakaUser language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Instant getDataOfRegistration() {
        return dataOfRegistration;
    }

    public ChakaUser dataOfRegistration(Instant dataOfRegistration) {
        this.dataOfRegistration = dataOfRegistration;
        return this;
    }

    public void setDataOfRegistration(Instant dataOfRegistration) {
        this.dataOfRegistration = dataOfRegistration;
    }

    public UserAccount getUserID() {
        return userID;
    }

    public ChakaUser userID(UserAccount userAccount) {
        this.userID = userAccount;
        return this;
    }

    public void setUserID(UserAccount userAccount) {
        this.userID = userAccount;
    }

    public Set<Transaction> getUserIDS() {
        return userIDS;
    }

    public ChakaUser userIDS(Set<Transaction> transactions) {
        this.userIDS = transactions;
        return this;
    }

    public ChakaUser addUserID(Transaction transaction) {
        this.userIDS.add(transaction);
        transaction.setChakaUser(this);
        return this;
    }

    public ChakaUser removeUserID(Transaction transaction) {
        this.userIDS.remove(transaction);
        transaction.setChakaUser(null);
        return this;
    }

    public void setUserIDS(Set<Transaction> transactions) {
        this.userIDS = transactions;
    }

    public Set<Invitation> getUserIDS() {
        return userIDS;
    }

    public ChakaUser userIDS(Set<Invitation> invitations) {
        this.userIDS = invitations;
        return this;
    }

    public ChakaUser addUserID(Invitation invitation) {
        this.userIDS.add(invitation);
        invitation.setChakaUser(this);
        return this;
    }

    public ChakaUser removeUserID(Invitation invitation) {
        this.userIDS.remove(invitation);
        invitation.setChakaUser(null);
        return this;
    }

    public void setUserIDS(Set<Invitation> invitations) {
        this.userIDS = invitations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChakaUser)) {
            return false;
        }
        return id != null && id.equals(((ChakaUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ChakaUser{" +
            "id=" + getId() +
            ", userID='" + getUserID() + "'" +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", country='" + getCountry() + "'" +
            ", city='" + getCity() + "'" +
            ", zip='" + getZip() + "'" +
            ", street='" + getStreet() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", language='" + getLanguage() + "'" +
            ", dataOfRegistration='" + getDataOfRegistration() + "'" +
            "}";
    }
}
