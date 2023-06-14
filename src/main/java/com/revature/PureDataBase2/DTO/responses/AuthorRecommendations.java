package com.revature.PureDataBase2.DTO.responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.revature.PureDataBase2.entities.PdLibrary;
import com.revature.PureDataBase2.entities.PdObject;

/**
 * The Principal class represents the authenticated user's principal
 * information.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorRecommendations {
    List<PdLibrary> libraries;
    List<PdObject> objects;
}
