package com.revature.yolp.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
@Table(name = "objects")
public class Object {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "library_id")
    private String libraryId;

    private String author;

    @Column(name = "library_version");
    private String libraryVersion;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    private String description;
}
