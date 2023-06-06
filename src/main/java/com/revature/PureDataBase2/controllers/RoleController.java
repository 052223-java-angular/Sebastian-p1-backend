package com.revature.PureDataBase2.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.PureDataBase2.DTO.requests.NewRoleRequest;
import com.revature.PureDataBase2.services.RoleService;
import com.revature.PureDataBase2.util.custom_exceptions.ResourceConflictException;
import com.revature.PureDataBase2.util.custom_exceptions.RoleNotFoundException;

import lombok.AllArgsConstructor;

/**
 * The RoleController class provides operations related to role management.
 */
@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;

    /**
     * Creates a new role.
     *
     * @param req the NewRoleRequest object containing role creation details
     * @return ResponseEntity with the HTTP status indicating the success or failure
     *         of the role creation
     */
    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestBody NewRoleRequest req) {
        if (!roleService.isUniqueRole(req.getName())) {
            throw new ResourceConflictException("Role " + req.getName() + " already exists");
        }

        roleService.saveRole(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
