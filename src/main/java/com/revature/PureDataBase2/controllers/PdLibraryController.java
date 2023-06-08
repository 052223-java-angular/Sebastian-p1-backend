package com.revature.PureDataBase2.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.revature.PureDataBase2.entities.PdLibrary;
import com.revature.PureDataBase2.entities.PdObject;
import com.revature.PureDataBase2.entities.User;
import com.revature.PureDataBase2.services.JWTService;
import com.revature.PureDataBase2.services.UserService;
import com.revature.PureDataBase2.services.PdLibraryService;
//import com.revature.PureDataBase2.util.custom_exceptions.ObjectNotFoundException;
import com.revature.PureDataBase2.util.custom_exceptions.ResourceConflictException;
import com.revature.PureDataBase2.DTO.requests.PdEditObject;

import jakarta.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/library")
public class PdLibraryController {
    // dependency injection ie. services
    private final JWTService tokenService;
    private final UserService userService;
    private final PdLibraryService pdLibraryService;

    @PostMapping("/create")
    public ResponseEntity<?> createLibrary(@RequestBody PdLibrary library,
            HttpServletRequest req) {
        // only users can create new library
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        // if library is not unique, throw exception
        User user = userService.getById(userId);
        if (!pdLibraryService.isUnique(library.getName())) {
            throw new ResourceConflictException("Library is not unique");
        }
        for(PdObject o : library.getObjects()) {
            o.setLibrary(library);
            o.setLastEditedBy(user);
        }
        pdLibraryService.create(library, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<PdLibrary>> getAllLibraries() {
        // return all libraries
        return ResponseEntity.status(HttpStatus.OK).body(pdLibraryService.getAll());
    }

    @GetMapping("/{libName}")
    public ResponseEntity<PdLibrary> getLibrary(@PathVariable String libName) {
        return ResponseEntity.status(HttpStatus.OK).body(pdLibraryService.getByName(libName));
    }

    @GetMapping("/{libName}/{objectName}")
    public ResponseEntity<PdObject> getObjectByLibrary(@PathVariable String libName,
        @PathVariable String objectName) {
        PdLibrary library = pdLibraryService.getByName(libName);
        return ResponseEntity.status(HttpStatus.OK).body(
            pdLibraryService.getObjectByNameAndLibrary(objectName, library));
    }

    @PostMapping("/{libName}/create")
    public ResponseEntity<?> saveObject(@PathVariable String libName,
        @RequestBody PdObject object, HttpServletRequest req) {
        // only users can create new library
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        // if library is not unique, throw exception
        User user = userService.getById(userId);
        PdLibrary library = pdLibraryService.getByName(libName);
        if(!pdLibraryService.isUniqueObjectName(object.getName(), library)) {
            throw new ResourceConflictException("object already exists at path");
        }
        object.setLibrary(library);
        pdLibraryService.saveObject(object, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PatchMapping("/{libName}/{oldName}")
    public ResponseEntity<?> updateObject(@PathVariable String libName, @PathVariable String oldName,
        @RequestBody PdEditObject editObject, HttpServletRequest req) {
        PdLibrary library = pdLibraryService.getByName(libName);

        PdObject prevObject = pdLibraryService.getObjectByNameAndLibrary(oldName, library);
        // only users can create new library
        String userId = tokenService.extractUserId(req.getHeader("auth-token")); 
        User user = userService.getById(userId);

        pdLibraryService.updateObject(editObject, prevObject, user, library);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
