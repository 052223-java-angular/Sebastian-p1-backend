package com.revature.PureDataBase2.util.custom_exceptions;

public class UserNotFoundException extends UnauthorizedException {
    public UserNotFoundException (String msg) {
        super(msg);
    }
}
