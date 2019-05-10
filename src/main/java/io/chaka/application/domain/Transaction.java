package io.chaka.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "transaction_id")
    private String transactionID;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "description")
    private String description;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "total")
    private String total;

    @ManyToOne
    @JsonIgnoreProperties("transactions")
    private ChakaUser chakaUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public Transaction transactionID(String transactionID) {
        this.transactionID = transactionID;
        return this;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getUserID() {
        return userID;
    }

    public Transaction userID(String userID) {
        this.userID = userID;
        return this;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDescription() {
        return description;
    }

    public Transaction description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public Transaction type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotal() {
        return total;
    }

    public Transaction total(String total) {
        this.total = total;
        return this;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ChakaUser getChakaUser() {
        return chakaUser;
    }

    public Transaction chakaUser(ChakaUser chakaUser) {
        this.chakaUser = chakaUser;
        return this;
    }

    public void setChakaUser(ChakaUser chakaUser) {
        this.chakaUser = chakaUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", transactionID='" + getTransactionID() + "'" +
            ", userID='" + getUserID() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", total='" + getTotal() + "'" +
            "}";
    }
}
