package com.fresco.faculty.service;

import com.fresco.faculty.model.UserModel;
import com.fresco.faculty.repository.RoleRepository;
import com.fresco.faculty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public class UserService {

      /*
   Implement the business logic for the UserService  operations in this class
   Make sure to add required annotations
    */


    private UserRepository userRepository;


    private RoleRepository roleRepository;

    //get user by email
    public UserModel getUserByEmail(Sthring email){
        return null;
    }

}
