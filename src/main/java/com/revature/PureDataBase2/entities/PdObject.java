package com.revature.PureDataBase2.entities;

import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "objects")
public class PdObject {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "library_id")
    @JsonIgnoreProperties("objects")
    private PdLibrary library;

    private String author;

    @Column(name = "library_version")
    private String libraryVersion;

    @ManyToOne
    @JoinColumn(name = "last_edited_by")
    @JsonIgnore
    private User lastEditedBy;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String helpText;

    @OneToMany(mappedBy = "object", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("object")
    // comment editing is done through the 'comment' endpoint
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<ObjectComment> comments;

    @OneToMany(mappedBy = "object", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("object")
    private Set<ObjectTag> objectTags;

    public PdObject(String name, PdLibrary library, User createdBy) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.library = library;
        this.author = "";
        this.libraryVersion = "";
        this.lastEditedBy = createdBy;
        this.description = "";
        this.helpText = "";
        this.comments = new HashSet<ObjectComment>();
        this.objectTags = new HashSet<ObjectTag>();
    }

    public PdObject() {
        this.id = UUID.randomUUID().toString();
        this.author = "";
        this.libraryVersion = "";
        this.description = "";
        this.helpText = "";
        this.comments = new HashSet<ObjectComment>();
        this.objectTags = new HashSet<ObjectTag>();
    }

}
