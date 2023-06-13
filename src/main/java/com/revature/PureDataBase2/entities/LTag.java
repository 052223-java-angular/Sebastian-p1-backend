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
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "l_tags")
public class LTag {
    @Id
    private String name;

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("tag")
    private Set<LibraryTag> libraryTags;

    public LTag(String name) {
        this.name = name;
        this.libraryTags = new HashSet<LibraryTag>();
    }

    public LTag() {
        this.name = "";
        this.libraryTags = new HashSet<LibraryTag>();
    }
}
