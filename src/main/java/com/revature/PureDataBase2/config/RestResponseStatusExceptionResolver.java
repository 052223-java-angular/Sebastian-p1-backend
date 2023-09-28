package com.revature.PureDataBase2.config;

import java.util.Date;

//import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RestResponseStatusExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res, Object obj,
        Exception exception) {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        res.setStatus(500);
        mav.addObject("timestamp", new Date(System.currentTimeMillis()));
        mav.addObject("message", exception.getMessage());

        return mav;
    }
}
