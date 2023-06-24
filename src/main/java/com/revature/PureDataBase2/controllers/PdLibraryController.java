package com.revature.PureDataBase2.controllers;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.revature.PureDataBase2.entities.PdLibrary;
import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.entities.User;
import com.revature.PureDataBase2.entities.ObjectComment;
import com.revature.PureDataBase2.entities.LibraryTag;
import com.revature.PureDataBase2.services.JWTService;
import com.revature.PureDataBase2.services.UserService;
import com.revature.PureDataBase2.services.PdLibraryService;
import com.revature.PureDataBase2.services.CommentService;
//import com.revature.PureDataBase2.util.custom_exceptions.ObjectNotFoundException;
import com.revature.PureDataBase2.util.custom_exceptions.ResourceConflictException;
import com.revature.PureDataBase2.util.custom_exceptions.UnauthorizedException;
import com.revature.PureDataBase2.DTO.requests.PdEditObject;
import com.revature.PureDataBase2.DTO.responses.LibrarySummary;
import com.revature.PureDataBase2.DTO.requests.PdEditLibrary;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/libraries")
public class PdLibraryController {
    // dependency injection ie. services
    private final JWTService tokenService;
    private final UserService userService;
    private final CommentService commentService;
    private final PdLibraryService pdLibraryService;

    @PostMapping
    public ResponseEntity<?> createLibrary(@RequestBody PdEditLibrary library,
            HttpServletRequest req) {
        // only users can create new library
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        // if library is not unique, throw exception
        User user = userService.getById(userId);
        if (!pdLibraryService.isUnique(library.getName())) {
            throw new ResourceConflictException("Library is not unique");
        }
        pdLibraryService.create(library, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<LibrarySummary>> getAllLibraries() {
        // return all libraries summary
        List<PdLibrary> libList = pdLibraryService.getAll();
        List<LibrarySummary> libSummaries = new ArrayList<LibrarySummary>();
        for(PdLibrary library : libList) {
            LibrarySummary curSum = new LibrarySummary();
            curSum.setName(library.getName());
            curSum.setAuthor(library.getAuthor());
            curSum.setDescription(library.getDescription());
            curSum.setRecentVersion(library.getRecentVersion());
            List<String> sumStrings = new ArrayList<String>();
            for(LibraryTag libTag : library.getLibraryTags()) {
                sumStrings.add(libTag.getTag().getName());
            }
            curSum.setTags(sumStrings);
            libSummaries.add(curSum);
        }
        return ResponseEntity.status(HttpStatus.OK).body(libSummaries);
    }

    @GetMapping("/{libName}")
    public ResponseEntity<PdLibrary> getLibrary(@PathVariable String libName) {
        return ResponseEntity.status(HttpStatus.OK).body(pdLibraryService.getByName(libName));
    }

    @GetMapping("/{libName}/{objectName}")
    public ResponseEntity<PdObject> getObjectByLibrary(@PathVariable String libName,
        @PathVariable String objectName) {
        return ResponseEntity.status(HttpStatus.OK).body(
            pdLibraryService.getObjectByNameAndLibraryName(objectName, libName));
    }

    @PostMapping("/{libName}")
    public ResponseEntity<?> saveObject(@PathVariable String libName,
        @RequestBody PdEditObject object, HttpServletRequest req) {
        // only users can create new object
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        // if library is not unique, throw exception
        User user = userService.getById(userId);
        PdLibrary library = pdLibraryService.getByName(libName);
        if(!pdLibraryService.isUniqueObjectName(object.getName(), libName)) {
            throw new ResourceConflictException("object already exists at path");
        }

        pdLibraryService.newObject(object, user, library);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{libName}")
    public ResponseEntity<?> updateLibrary(@PathVariable String libName,
        @RequestBody PdEditLibrary editLibrary, HttpServletRequest req) {

        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        User user = userService.getById(userId);

        PdLibrary prevLibrary = pdLibraryService.getByName(libName);

        pdLibraryService.updateLibrary(editLibrary, prevLibrary, user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{libName}/{oldName}")
    public ResponseEntity<?> updateObject(@PathVariable String libName, @PathVariable String oldName,
        @RequestBody PdEditObject editObject, HttpServletRequest req) {

        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        User user = userService.getById(userId);

        PdObject prevObject = pdLibraryService.getObjectByNameAndLibraryName(oldName, libName);

        pdLibraryService.updateObject(editObject, prevObject, user, libName);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Transactional
    @DeleteMapping("/{libName}/{objectName}")
    public ResponseEntity<?> deleteObject(@PathVariable String libName, @PathVariable String objectName,
        HttpServletRequest req) {

        // only users can delete library
        tokenService.extractUserId(req.getHeader("auth-token")); 

        pdLibraryService.deleteObjectByNameAndLibraryName(objectName, libName);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
 
    @PostMapping("/{libName}/{objectName}/comment")
    public ResponseEntity<?> postComment(@PathVariable String libName, @PathVariable String objectName,
        HttpServletRequest req, @RequestBody String comment) {
        PdObject object = pdLibraryService.getObjectByNameAndLibraryName(objectName, libName);

        // only users can add comment
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        User user = userService.getById(userId);
        commentService.create(comment, object, user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{libName}/{objectName}/comment/{id}")
    public ResponseEntity<?> editComment(@PathVariable String libName, @PathVariable String objectName,
        @PathVariable String id, HttpServletRequest req, @RequestBody String editComment) {

        // only users can add comment
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        ObjectComment prevComment = commentService.getById(id);
        if(!prevComment.getUser().getId().equals(userId))
            throw new UnauthorizedException("unable to update comment: unauthorized");

        prevComment.setComment(editComment);
        commentService.update(prevComment);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{libName}/{objectName}/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable String libName, @PathVariable String objectName,
        @PathVariable String id, HttpServletRequest req) {

        // only users can add comment
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        ObjectComment prevComment = commentService.getById(id);
        if(!prevComment.getUser().getId().equals(userId))
            throw new UnauthorizedException("unable to delete comment: unauthorized");

        commentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
