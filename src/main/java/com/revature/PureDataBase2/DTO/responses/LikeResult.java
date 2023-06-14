package com.revature.PureDataBase2.DTO.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Principal class represents the authenticated user's principal
 * information.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
@Setter
public class LikeResult {
    String type;
    String value;
}
