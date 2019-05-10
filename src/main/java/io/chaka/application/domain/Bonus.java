package io.chaka.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bonus.
 */
@Entity
@Table(name = "bonus")
public class Bonus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "bonus_id")
    private String bonusID;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "coins")
    private Double coins;

    @ManyToOne
    @JsonIgnoreProperties("bonuses")
    private UserAccount userAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBonusID() {
        return bonusID;
    }

    public Bonus bonusID(String bonusID) {
        this.bonusID = bonusID;
        return this;
    }

    public void setBonusID(String bonusID) {
        this.bonusID = bonusID;
    }

    public String getUserID() {
        return userID;
    }

    public Bonus userID(String userID) {
        this.userID = userID;
        return this;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Double getCoins() {
        return coins;
    }

    public Bonus coins(Double coins) {
        this.coins = coins;
        return this;
    }

    public void setCoins(Double coins) {
        this.coins = coins;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public Bonus userAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bonus)) {
            return false;
        }
        return id != null && id.equals(((Bonus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Bonus{" +
            "id=" + getId() +
            ", bonusID='" + getBonusID() + "'" +
            ", userID='" + getUserID() + "'" +
            ", coins=" + getCoins() +
            "}";
    }
}
