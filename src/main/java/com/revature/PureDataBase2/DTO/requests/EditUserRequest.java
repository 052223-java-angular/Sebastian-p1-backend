package com.revature.PureDataBase2.DTO.requests;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserRequest {
    private String email = "";
    private String username = "";
}
