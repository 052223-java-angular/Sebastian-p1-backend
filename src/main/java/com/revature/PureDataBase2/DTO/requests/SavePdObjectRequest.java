package com.revature.PureDataBase2.DTO.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO for getting object edits- mirrors PdObject without the constructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SavePdObjectRequest {
    private String username;
    private String password;
}
