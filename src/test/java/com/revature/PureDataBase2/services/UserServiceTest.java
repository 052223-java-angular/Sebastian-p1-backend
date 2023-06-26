package com.revature.PureDataBase2.services;

import com.revature.PureDataBase2.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService userService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private RoleService roleService;

    @BeforeEach
    public void setup() {
        userService = new UserService(roleService, userRepo);
    }

    @Test
    public void isValidUsernameTest() {
        assertTrue(userService.isValidUsername("bob_4888"));
        assertFalse(userService.isValidUsername("bb_4888"));
        assertFalse(userService.isValidUsername("bb_.4888"));
        assertFalse(userService.isValidUsername("bob_4888_"));
        assertTrue(userService.isValidUsername("bob_48.88"));
        assertFalse(userService.isValidUsername("$ob_4888"));
        assertTrue(userService.isValidUsername("94950236"));
        assertTrue(userService.isValidUsername("sntiemik"));
    }

    @Test
    public void isValidPasswordTest() {
        assertTrue(userService.isValidPassword("pizzapizzapie9"));
        assertFalse(userService.isValidPassword("pizzap9"));
        assertFalse(userService.isValidPassword("848484839"));
        assertFalse(userService.isValidPassword("cihicmumi"));
        assertFalse(userService.isValidPassword("merom-rocehim4"));
        assertFalse(userService.isValidPassword("merom$rocehim4"));
        assertTrue(userService.isValidPassword("8meromrocehim5"));
    }

    @Test
    public void isValidEmail() {
        assertFalse(userService.isValidEmail("harmony"));
        assertTrue(userService.isValidEmail("billybob@mnmnm.net"));
        assertFalse(userService.isValidEmail("mnmnm.net"));
        assertFalse(userService.isValidEmail("@mnmnm.net"));
    }
}
