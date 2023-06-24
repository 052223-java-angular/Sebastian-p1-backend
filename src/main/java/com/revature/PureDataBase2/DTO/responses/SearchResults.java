package com.revature.PureDataBase2.DTO.responses;
import java.util.List;

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
    List<String> libraryResults;
    List<String> objectResults;
    List<String> objTagResults;
    List<String> libTagResults;
    List<String> authorResults;
}
