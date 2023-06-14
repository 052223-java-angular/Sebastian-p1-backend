package com.revature.PureDataBase2.entities;

import java.util.UUID;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

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
@Table(name = "likes")
public class Like implements Comparable<Like> {
    public enum EntityType {OBJECT, LIBRARY, AUTHOR}

    @Id
    @JsonIgnore
    private String id;

    @Column(nullable = false)
    private Date time;

    @Column(name = "entity_type")
    @Enumerated(EnumType.ORDINAL)
    private EntityType entityType;

    @Column(name = "entity_id")
    private String entityId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("likes")
    private User user;

    // must be comparable for user history treeset

    public int compareTo(Like compared) {
        return this.time.compareTo(compared.getTime());
    }

    public Like(EntityType entityType, String entityId, User user) {
        this.id = UUID.randomUUID().toString();
        this.time = new Date();
        this.entityType = entityType;
        this.entityId = entityId;
        this.user = user;
    }

}
