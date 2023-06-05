package com.revature.PureDataBase2.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    // set the column username to username
    @Column(name = "username", nullable = false)
    private String username;

    // by default column password is password
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<ObjectComment> objectComments;

    @OneToMany(mappedBy = "lastEditedBy", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<PdLibrary> lastEditedByLibraries;

    @OneToMany(mappedBy = "lastEditedBy", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<PdObject> lastEditedByObjects;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonBackReference
    private Role role;

    public User(String username, String password, Role role) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.objectComments = new HashSet<ObjectComment>();
        this.lastEditedByLibraries = new HashSet<PdLibrary>();
        this.lastEditedByObjects = new HashSet<PdObject>();
        this.role = role;
    }

}
