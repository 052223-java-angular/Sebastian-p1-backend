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
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "objects")
public class PdObject {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "library_id")
    @JsonBackReference
    private PdLibrary library;

    private String author;

    @Column(name = "library_version")
    private String libraryVersion;

    @ManyToOne
    @JoinColumn(name = "last_edited_by")
    @JsonBackReference
    private User lastEditedBy;

    private String description;

    @OneToMany(mappedBy = "object", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<ObjectComment> comments;

    public PdObject(String name, PdLibrary library, User createdBy) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.library = library;
        this.author = "";
        this.libraryVersion = "";
        this.lastEditedBy = createdBy;
        this.description = "";
        this.comments = new HashSet<ObjectComment>();
    }
}
