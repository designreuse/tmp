package com.softserve.osbb.tenants.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Roman on 23.07.2016.
 */

@Entity
@Table(name = "option")
public class Option implements Serializable{

    private Integer optionId;
    private String description;
    private Vote vote;
    private List<User> users;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // @JsonBackReference
    //@XmlTransient
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vote_id", nullable = false)
    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    /*@JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "options")*/
    //@ManyToMany(mappedBy = "options")


    @ManyToMany(mappedBy = "options")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return "Option{" +
                "optionId=" + optionId +
                ", description='" + description + '\'' +
                '}';
    }
}