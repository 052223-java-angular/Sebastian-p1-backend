package com.revature.PureDataBase2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.PureDataBase2.DTO.requests.NewLoginRequest;
import com.revature.PureDataBase2.DTO.requests.NewUserRequest;
import com.revature.PureDataBase2.DTO.responses.Principal;
import com.revature.PureDataBase2.services.UserService;
import com.revature.PureDataBase2.services.JWTService;
import com.revature.PureDataBase2.util.custom_exceptions.ResourceConflictException;
import com.revature.PureDataBase2.util.custom_exceptions.InvalidFormatException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

/**
 * The AuthController class provides authentication-related operations.
 */
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JWTService tokenService;
    /**
     * Registers a new user.
     *
     * @param req the NewUserRequest object containing user registration details
     * @return ResponseEntity with the HTTP status indicating the success or failure
     *         of the registration
     */
    @PostMapping("/register")
    public ResponseEntity<Principal> registerUser(@RequestBody NewUserRequest req) {
        // if username is not valid, throw exception
        if (!userService.isValidUsername(req.getUsername())) {
            throw new InvalidFormatException(
                    "Username needs to be 8-20 characters long and can only contain letters, numbers, periods, and underscores");
        }

        // if username is not unique, throw exception
        if (!userService.isUniqueUsername(req.getUsername())) {
            throw new ResourceConflictException("Username is not unique");
        }

        // if password is not valid, throw exception
        if (!userService.isValidPassword(req.getPassword())) {
            throw new InvalidFormatException(
                    "Password needs to be at least 8 characters long and contain at least one letter and one number");
        }

        // if password and confirm password do not match, throw exception
        if (!userService.isSamePassword(req.getPassword(), req.getConfirmPassword())) {
            throw new ResourceConflictException("Passwords do not match");
        }

        // register user
        Principal principal = new Principal(userService.registerUser(req));
        String token = tokenService.generateToken(principal);
        principal.setToken(token);

        // return 201 - CREATED
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Principal> login(@RequestBody NewLoginRequest req) {
        // userservice to call login method
        Principal principal = userService.login(req);

        // create a jwt token
        String token = tokenService.generateToken(principal);

        principal.setToken(token);

        // return status ok and return principal object
        return ResponseEntity.status(HttpStatus.OK).body(principal);
    }

    @GetMapping("/check")
    public ResponseEntity<?> check(HttpServletRequest sreq) {
        // userservice to call login method
        
        tokenService.extractUsername(sreq.getHeader("auth-token"));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
