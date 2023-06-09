package com.revature.PureDataBase2.DTO.requests;

//import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/* object for transferring unique library/object location */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ObjAddress {
    private String libraryName;
    private String objectName;

    private boolean present = true;
}
