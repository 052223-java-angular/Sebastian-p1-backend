package com.revature.PureDataBase2.DTO.requests;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class EditUserRequest {
    String email;
    String username;
    MultipartFile image;
}
