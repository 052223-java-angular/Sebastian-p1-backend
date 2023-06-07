package com.revature.PureDataBase2.entities;

import java.util.UUID;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "object_comments")
public class ObjectComment {
    @Id
    @JsonIgnore
    private String id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false, name = "time_posted")
    private Date timePosted;

    @ManyToOne
    @JoinColumn(name = "object_id")
    @JsonIgnoreProperties("comments")
    private PdObject object;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("objectComments")
    @JsonBackReference
    private User user;

    public ObjectComment(String comment, PdObject object, User user) {
        this.id = UUID.randomUUID().toString();
        this.comment = comment;
        this.timePosted = new Date();
        this.object = object;
        this.user = user;
    }

}
