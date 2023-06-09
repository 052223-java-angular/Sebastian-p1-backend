package com.revature.PureDataBase2.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.revature.PureDataBase2.entities.PdLibrary;
import com.revature.PureDataBase2.entities.LTag;
import com.revature.PureDataBase2.services.JWTService;
import com.revature.PureDataBase2.services.LTagService;
import com.revature.PureDataBase2.util.custom_exceptions.InvalidFormatException;
import com.revature.PureDataBase2.util.custom_exceptions.ResourceConflictException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/library_tags")
public class LTagController {
    // dependency injection ie. services
    private final JWTService tokenService;
    private final LTagService lTagService;
    private final Logger logger = LoggerFactory.getLogger(LTagController.class);

    public LTagController(JWTService tokenService, LTagService lTagService) {
        this.tokenService = tokenService;
        this.lTagService = lTagService;
    }

    @PostMapping("/{lTagString}")
    public ResponseEntity<?> createTag(@PathVariable String lTagString,
            HttpServletRequest req) {
        // only users can create new tag
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        logger.trace("creating new lTag: " + lTagString + " from user " + userId);
        // if library is not unique, throw exception
        lTagString = lTagString.toLowerCase();
        if (!lTagService.isUnique(lTagString)) {
            throw new ResourceConflictException("Tag is not unique");
        }
        if(!lTagService.isValidLTag(lTagString))
            throw new InvalidFormatException("tag can only contain letters and '-' (hyphen)");
        LTag lTag = new LTag(lTagString);
        lTagService.create(lTag);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllTags() {
        // return all names
        return ResponseEntity.status(HttpStatus.OK).body(lTagService.getAllNames());
    }

    @GetMapping("{lTagName}")
    public ResponseEntity<LTag> getLTag(@PathVariable String lTagName) {
        return ResponseEntity.status(HttpStatus.OK).body(lTagService.getByName(lTagName));
    }

    @GetMapping("{lTagName}/libraries")
    public ResponseEntity<List<PdLibrary>> getObjectsByTag(@PathVariable String lTagName) {
        List<PdLibrary> objects = lTagService.getLibrariesByTagName(lTagName);
        return ResponseEntity.status(HttpStatus.OK).body(objects);
    }

}
