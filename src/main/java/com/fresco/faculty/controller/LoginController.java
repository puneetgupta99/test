package com.fresco.faculty.controller;

import com.fresco.faculty.dto.LoginDTO;
import com.fresco.faculty.security.JWTUtil;
import com.fresco.faculty.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;


public class LoginController {

    /*
         This controller would be responsible for the login endpoints
         Add the required annotations and create the endpoints
    */

    private AuthenticationManager authenticationManager;

    LoginService loginService;

    private JWTUtil jwtTokenUtil;

    @PostMapping("/user/login")
    public Object authenticateUser( LoginDTO authenticationRequest) throws Exception {

        return null;
    }


}

