package com.revature.PureDataBase2.entities;

import java.util.UUID;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "history")
public class HistoryItem implements Comparable<HistoryItem> {
    public enum EntityType {OBJECT, LIBRARY}

    @Id
    private String id;

    @Column(nullable = false)
    private Date time;

    @Column(name = "entity_type")
    @Enumerated(EnumType.ORDINAL)
    private EntityType entityType;

    @ManyToOne
    @JoinColumn(name = "entity_id")
    private String entityId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    // must be comparable for user history treeset

    public int compareTo(HistoryItem compared) {
        return this.time.compareTo(compared.getTime());
    }

    public HistoryItem(EntityType entityType, String entityId, User user) {
        this.id = UUID.randomUUID().toString();
        this.time = new Date();
        this.entityType = entityType;
        this.entityId = entityId;
        this.user = user;
    }

}
