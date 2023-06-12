package com.revature.PureDataBase2.DTO.responses;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Principal class represents the authenticated user's principal
 * information.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorResults {
    String authorName;
    String type;
    Serializable content;
}
