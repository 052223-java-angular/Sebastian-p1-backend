package com.revature.PureDataBase2.DTO.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewLibraryRequest {
    private String name;
    private String token;
}
