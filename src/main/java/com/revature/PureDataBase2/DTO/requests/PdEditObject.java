package com.revature.PureDataBase2.DTO.requests;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PdEditObject {
    private String name;
    @JsonProperty("libraryVersion")
    private String libVersion;
    @JsonProperty("library")
    private String libName;
    private String author;
    private String description;
    private String helpText;
    private Set<String> objectTags;
}
