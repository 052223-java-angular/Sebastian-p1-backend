package com.revature.PureDataBase2.entities;

import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
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
@Table(name = "libraries")
public class Library {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String author;

    @Column(name = "recent_version")
    private String recentVersion;

    @ManyToOne
    @JoinColumn(name = "last_edited_by")
    @JsonBackReference
    private User lastEditedBy;

    @OneToMany(mappedBy = "library", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<PdObject> objects;
    
    public Library (String name, User createdBy) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.author = "";
        this.recentVersion = "";
        this.objects = new HashSet<PdObject>();
        this.lastEditedBy = createdBy;
    }
}