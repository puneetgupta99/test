package com.fresco.faculty.security;

import com.fresco.faculty.model.UserModel;
import com.fresco.faculty.repository.RoleRepository;
import com.fresco.faculty.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

@Component
public class JWTUtil implements Serializable {

    /**
     *JWTUtil Can be used for JWT operations
     */


    private static final long serialVersionUID = 654352132132L;

    public static final long JWT_TOKEN_VALIDITY = 500 * 60 * 60;

    private final String secretKey = "randomkey123";

    /*
    Gets the Username(email) of the user from token
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /*
    Retrieves the expiry of the token
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return null;
    }


    /*
    Secret key will be required for retrieving data from token
     */
    private Claims getAllClaimsFromToken(String token) {
        return null;
    }


    /*
    Check if the token has expired
     */
    private Boolean isTokenExpired(String token) {
        return null;
    }

    UserService userService;

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        return null;
    }


    /*
    Generate the token from the claims and required details
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return null;
    }


    /*
    Check if the provided JWT token is valid or not
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        return null;
    }


}