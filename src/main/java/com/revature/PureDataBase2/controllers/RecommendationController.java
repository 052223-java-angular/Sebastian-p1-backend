package com.revature.PureDataBase2.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.PureDataBase2.services.JWTService;
import com.revature.PureDataBase2.services.RecommendationService;
//import com.revature.PureDataBase2.DTO.responses.AuthorRecommendations;

import jakarta.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    private final JWTService tokenService;
    private final RecommendationService recommendationService;

    @GetMapping("/objects")
    public ResponseEntity<List<String>> objectRecs(HttpServletRequest req) {
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        return ResponseEntity.status(HttpStatus.OK).body(recommendationService.getByObject(userId));
    }

    @GetMapping("/libraries")
    public ResponseEntity<List<String>> libraryRecs(HttpServletRequest req) {
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        return ResponseEntity.status(HttpStatus.OK).body(recommendationService.getByLibrary(userId));
    }
/*
    @GetMapping("/authors")
    public ResponseEntity<Set<AuthorRecommendations>> authorRecs(HttpServletRequest req) {
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        AuthorRecommendations authorRecs;
        authorRecs.setLibraries(recommendationService.getLibrariesByAuthor(userId));
        authorRecs.setObjects(recommendationService.getObjectsByAuthor(userId));
        return ResponseEntity.status(HttpStatus.OK).body();
    } */
}
