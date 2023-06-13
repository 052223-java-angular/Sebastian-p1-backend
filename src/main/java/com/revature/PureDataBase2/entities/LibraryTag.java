package com.revature.PureDataBase2.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "library_tags")
public class LibraryTag {
    @Id
    @JsonIgnore
    private String id;

    @ManyToOne
    @JoinColumn(name = "library_id")
    @JsonIgnoreProperties("libraryTags")
    private PdLibrary library;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @JsonIgnoreProperties("libraryTags")
    private LTag tag;

    public LibraryTag(PdLibrary library, LTag lTag) {
        this.id = UUID.randomUUID().toString();
        this.library = library;
        this.tag = lTag;
    }

}
