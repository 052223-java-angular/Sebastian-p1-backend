package com.revature.PureDataBase2.entities;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "users")
public class User {
    @Id
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "user")
    private Set<ObjectComment> objectComments;

    @OneToMany(mappedBy = "lastEditedBy", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<PdLibrary> lastEditedByLibraries;

    @OneToMany(mappedBy = "lastEditedBy", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<PdObject> lastEditedByObjects;

    @ManyToOne
    @JoinColumn(nullable = false, name = "role_id")
    @JsonBackReference(value="user-role")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role;

    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(columnDefinition = "boolean default false")
    private boolean hasProfilePic;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Like> likes;

    public User(String username, String password, Role role) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.objectComments = new HashSet<ObjectComment>();
        this.lastEditedByLibraries = new HashSet<PdLibrary>();
        this.lastEditedByObjects = new HashSet<PdObject>();
        this.role = role;
        this.email = "";
        this.hasProfilePic = false;
        this.likes = new TreeSet<Like>();
    }
}
