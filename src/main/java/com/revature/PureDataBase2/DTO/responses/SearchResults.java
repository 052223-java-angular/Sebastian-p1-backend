package com.revature.PureDataBase2.DTO.responses;
import java.util.List;

import com.revature.PureDataBase2.entities.PdLibrary;
import com.revature.PureDataBase2.entities.PdObject;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Principal class represents the authenticated user's principal
 * information.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchResults {
    List<PdLibrary> libraryResults;
    List<PdObject> objectResults;
    List<PdObject> tagResults;
    List<PdLibrary> authorResults;
}
