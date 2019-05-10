package io.chaka.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Task entity.
 * @author The JHipster team.
 */
@ApiModel(description = "Task entity. @author The JHipster team.")
@Entity
@Table(name = "invitation")
public class Invitation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "invitation_id")
    private String invitationID;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JsonIgnoreProperties("invitations")
    private ChakaUser chakaUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvitationID() {
        return invitationID;
    }

    public Invitation invitationID(String invitationID) {
        this.invitationID = invitationID;
        return this;
    }

    public void setInvitationID(String invitationID) {
        this.invitationID = invitationID;
    }

    public String getUserID() {
        return userID;
    }

    public Invitation userID(String userID) {
        this.userID = userID;
        return this;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public Invitation email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public Invitation status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ChakaUser getChakaUser() {
        return chakaUser;
    }

    public Invitation chakaUser(ChakaUser chakaUser) {
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
        if (!(o instanceof Invitation)) {
            return false;
        }
        return id != null && id.equals(((Invitation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Invitation{" +
            "id=" + getId() +
            ", invitationID='" + getInvitationID() + "'" +
            ", userID='" + getUserID() + "'" +
            ", email='" + getEmail() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
