package com.fresco.faculty.service;

import com.fresco.faculty.model.UserModel;
import com.fresco.faculty.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginService implements UserDetailsService {

      /*
   Implement the business logic for the LoginService  operations in this class
   Make sure to add required annotations
    */


    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return null;
    }

    private UserDetails buildUserForAuthentication(UserModel user, List<GrantedAuthority> authorities) {
        return null;
    }

    private List<GrantedAuthority> buildUserAuthority(String userRole) {
        return null;
    }

}

