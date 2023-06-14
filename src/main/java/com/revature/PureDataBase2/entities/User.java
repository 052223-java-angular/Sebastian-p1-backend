package com.revature.PureDataBase2.entities;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonIgnore
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "user")
    private Set<ObjectComment> objectComments;

    @OneToMany(mappedBy = "lastEditedBy", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<PdLibrary> lastEditedByLibraries;

    @OneToMany(mappedBy = "lastEditedBy", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<PdObject> lastEditedByObjects;

    @ManyToOne
    @JoinColumn(nullable = false, name = "role_id")
    @JsonBackReference(value="user-role")
    private Role role;

    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
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
        this.likes = new TreeSet<Like>();
    }
}
