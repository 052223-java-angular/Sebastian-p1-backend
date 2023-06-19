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
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "object_tags")
public class ObjectTag {
    @Id
    @JsonIgnore
    private String id;

    @ManyToOne
    @JoinColumn(name = "object_id")
    @JsonIgnoreProperties({"objectTags", "helpText"})
    private PdObject object;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @JsonIgnoreProperties("objectTags")
    private Tag tag;

    public ObjectTag(PdObject object, Tag tag) {
        this.id = UUID.randomUUID().toString();
        this.object = object;
        this.tag = tag;
    }

    public ObjectTag() {
        this.id = UUID.randomUUID().toString();
        this.object = null;
        this.tag = null;
    }
}
