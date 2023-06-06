package com.revature.PureDataBase2.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.PureDataBase2.entities.PdLibrary;
import com.revature.PureDataBase2.DTO.requests.NewLibraryRequest;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/library")
public class PdLibraryController {
    // dependency injection ie. services

    @PostMapping("/create")
    public ResponseEntity<?> createLibrary(@RequestBody NewLibraryRequest req, HttpServletRequest sreq) {
        // only users can create new library

        String token = sreq.getHeader("auth-token");

        // get token from req
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<PdLibrary>> getAllLibraries() {
        // return all restaurants
        return null;
    }
}
