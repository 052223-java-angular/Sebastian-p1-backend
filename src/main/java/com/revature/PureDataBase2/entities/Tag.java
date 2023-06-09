package com.revature.PureDataBase2.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "tags")
public class Tag {
    @Id
    private String name;

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("tag")
    private Set<ObjectTag> objectTags;

    public Tag(String name) {
        this.name = name;
        this.objectTags = new HashSet<ObjectTag>();
    }
}
