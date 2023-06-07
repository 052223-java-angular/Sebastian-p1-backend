package com.revature.PureDataBase2.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.revature.PureDataBase2.util.custom_exceptions.ResourceConflictException;
import com.revature.PureDataBase2.util.custom_exceptions.RoleNotFoundException;
import com.revature.PureDataBase2.util.custom_exceptions.UserNotFoundException;
import com.revature.PureDataBase2.util.custom_exceptions.UnauthorizedException;

@RestControllerAdvice
public class ExceptionController {

    /**
     * Exception handler for ResourceConflictException.
     *
     * @param e the ResourceConflictException to handle
     * @return ResponseEntity with the error message and status code indicating
     *         resource conflict
     */
    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<Map<String, Object>> handleResourceConflictException(ResourceConflictException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date(System.currentTimeMillis()));
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date(System.currentTimeMillis()));
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
    }

    /**
     * Exception handler for RoleNotFoundException.
     *
     * @param e the RoleNotFoundException to handle
     * @return ResponseEntity with the error message and status code indicating role
     *         not found
     */
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleRoleNotFoundException(RoleNotFoundException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date(System.currentTimeMillis()));
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(map);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(UnauthorizedException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date(System.currentTimeMillis()));
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(map);
    }

}
