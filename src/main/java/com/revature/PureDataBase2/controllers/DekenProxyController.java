package com.revature.PureDataBase2.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.entities.User;
import com.revature.PureDataBase2.services.DekenService;
import com.revature.PureDataBase2.services.JWTService;
import com.revature.PureDataBase2.services.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/deken")
public class DekenProxyController {
    private final DekenService dekenService;
    private final JWTService tokenService;
    private final UserService userService;

    @GetMapping("{libName}")
    ResponseEntity<String> getUrl(@PathVariable String libName) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(dekenService.getUrlForLibrary(libName));
    }

    @PutMapping("/update")
    ResponseEntity<List<PdObject>> updateFromDeken(@RequestBody String url,
            @RequestHeader("auth-token") String token) throws Exception {
        String userId = tokenService.extractUserId(token); 
        // if library is not unique, throw exception
        User user = userService.getById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(dekenService.updateLib(url, user));
    }
}
