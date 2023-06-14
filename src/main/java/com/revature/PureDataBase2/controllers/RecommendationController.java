package com.revature.PureDataBase2.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.services.JWTService;
import com.revature.PureDataBase2.services.RecommendationService;

import jakarta.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    private final JWTService tokenService;
    private final RecommendationService recommendationService;

    @GetMapping("/objects")
    public ResponseEntity<Set<PdObject>> objectRecs(HttpServletRequest req) {
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        return ResponseEntity.status(HttpStatus.OK).body(recommendationService.getByObject(userId));
    }
}
