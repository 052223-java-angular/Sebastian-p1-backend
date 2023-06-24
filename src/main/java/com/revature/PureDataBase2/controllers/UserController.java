
package com.revature.PureDataBase2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.PureDataBase2.entities.User;
import com.revature.PureDataBase2.services.UserService;
import com.revature.PureDataBase2.services.JWTService;
import com.revature.PureDataBase2.util.custom_exceptions.ResourceConflictException;
import com.revature.PureDataBase2.util.custom_exceptions.InvalidFormatException;

import lombok.AllArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;
/**
 * The RoleController class provides operations related to role management.
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final JWTService tokenService;

    @PutMapping(value = "/edit")
    public ResponseEntity<?> updateUser(@RequestParam(required = false) String email,
        @RequestParam(name = "username", required = false) String newUsername,
        @RequestParam(required = false) MultipartFile image,
        HttpServletRequest req) {
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        User user = userService.getById(userId);
        if(newUsername == null) newUsername = "";
        if(email == null) email = "";
        if(!newUsername.isEmpty() && !newUsername.equals(user.getUsername())) {
            if(!userService.isUniqueUsername(newUsername)) {
                throw new ResourceConflictException("username already exists");
            }
            if(!userService.isValidUsername(newUsername)) {
                throw new InvalidFormatException(
                    "Username needs to be 8-20 characters long and can only contain letters, numbers, " +
                    "periods, and underscores");
            }
            user.setUsername(newUsername);
        }
        if(!email.isEmpty() && !email.equals(user.getEmail())) {
            if(!userService.isValidEmail(email))
                throw new InvalidFormatException("invalid email");
            user.setEmail(email);
        }
        if(image != null) {
            userService.writeProfilePic(image, user);
        }
        userService.save(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProfilePic(HttpServletRequest req) {
        String userId = tokenService.extractUserId(req.getHeader("auth-token"));
        userService.deleteProfilePic(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/me")
    public ResponseEntity<User> thisUser(HttpServletRequest req) {
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        User user = userService.getById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
