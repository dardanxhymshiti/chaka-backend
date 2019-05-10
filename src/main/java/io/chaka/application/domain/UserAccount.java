package io.chaka.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "user_account")
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "account_id")
    private String accountID;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "bilane")
    private Double bilane;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "userAccount")
    private Set<Bonus> userIDS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountID() {
        return accountID;
    }

    public UserAccount accountID(String accountID) {
        this.accountID = accountID;
        return this;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getUserID() {
        return userID;
    }

    public UserAccount userID(String userID) {
        this.userID = userID;
        return this;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Double getBilane() {
        return bilane;
    }

    public UserAccount bilane(Double bilane) {
        this.bilane = bilane;
        return this;
    }

    public void setBilane(Double bilane) {
        this.bilane = bilane;
    }

    public String getStatus() {
        return status;
    }

    public UserAccount status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Bonus> getUserIDS() {
        return userIDS;
    }

    public UserAccount userIDS(Set<Bonus> bonuses) {
        this.userIDS = bonuses;
        return this;
    }

    public UserAccount addUserID(Bonus bonus) {
        this.userIDS.add(bonus);
        bonus.setUserAccount(this);
        return this;
    }

    public UserAccount removeUserID(Bonus bonus) {
        this.userIDS.remove(bonus);
        bonus.setUserAccount(null);
        return this;
    }

    public void setUserIDS(Set<Bonus> bonuses) {
        this.userIDS = bonuses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAccount)) {
            return false;
        }
        return id != null && id.equals(((UserAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
            "id=" + getId() +
            ", accountID='" + getAccountID() + "'" +
            ", userID='" + getUserID() + "'" +
            ", bilane=" + getBilane() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
