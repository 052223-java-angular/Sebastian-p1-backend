package com.revature.PureDataBase2.DTO.requests;
import com.revature.PureDataBase2.DTO.responses.Principal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewLibraryRequest {
    private Principal principal;
    private String libraryName;
}
