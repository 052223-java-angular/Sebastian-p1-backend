package com.revature.PureDataBase2.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
@Table(name = "roles")
public class Role {
    @Id
    @JsonIgnore
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @JsonManagedReference(value="user-role")
    private Set<User> users;

    public Role(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.users = new HashSet<>();
    }
}
