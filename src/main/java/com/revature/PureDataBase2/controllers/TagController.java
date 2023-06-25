package com.revature.PureDataBase2.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.entities.Tag;
import com.revature.PureDataBase2.services.JWTService;
import com.revature.PureDataBase2.services.TagService;
import com.revature.PureDataBase2.util.custom_exceptions.InvalidFormatException;
import com.revature.PureDataBase2.util.custom_exceptions.ResourceConflictException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/object_tags")
public class TagController {
    // dependency injection ie. services
    private final JWTService tokenService;
    private final TagService tagService;
    private final Logger logger = LoggerFactory.getLogger(TagController.class);

    public TagController(JWTService tokenService, TagService tagService) {
        this.tokenService = tokenService;
        this.tagService = tagService;
    }

    @PostMapping("/{tagString}")
    public ResponseEntity<?> createTag(@PathVariable String tagString,
            HttpServletRequest req) {
        // only users can create new tag
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        logger.trace("new object tag " + tagString + " from user " + userId);
        // if library is not unique, throw exception
        tagString = tagString.toLowerCase();
        if (!tagService.isUnique(tagString)) {
            throw new ResourceConflictException("Tag is not unique");
        }
        if(!tagService.isValidTag(tagString))
            throw new InvalidFormatException("tag can only contain letters and '-' (hyphen)");
        Tag tag = new Tag(tagString);
        tagService.create(tag);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllTags() {
        // return all names
        return ResponseEntity.status(HttpStatus.OK).body(tagService.getAllNames());
    }

    @GetMapping("{tagName}")
    public ResponseEntity<Tag> getTag(@PathVariable String tagName) {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.getByName(tagName));
    }

    @GetMapping("{tagName}/objects")
    public ResponseEntity<List<PdObject>> getObjectsByTag(@PathVariable String tagName) {
        List<PdObject> objects = tagService.getObjectsByTagName(tagName);
        return ResponseEntity.status(HttpStatus.OK).body(objects);
    }

    /* edit/create */
    /* implement later if there's time
    @PutMapping("{tagName}")
    public ResponseEntity<List<ObjAddress>> addAssociations(@PathVariable String tagName,
        add user validation here
        @RequestBody List<ObjAddress> objs) {
        objs = tagService.associateObjectTags(tagName, objs);
        return ResponseEntity.status(HttpStatus.OK).body(objs);
    }*/
}
