package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Chalienko on 15.04.2016.
 */

@Entity
@Table(name = "social_network")
public class SocialNetwork implements Serializable {
    private static final long serialVersionUID = 1244579714594167060L;

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "title")
    private String title;

    public SocialNetwork() {
    }

    public SocialNetwork(String title) {
        this.title = title;
    }

    public SocialNetwork(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SocialNetworkEnum: " +
                " title= " + title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SocialNetwork that = (SocialNetwork) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(title, that.title)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(title)
                .toHashCode();
    }
}
