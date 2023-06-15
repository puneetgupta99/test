package com.fresco.faculty.service;

import com.fresco.faculty.model.FacultyModel;
import com.fresco.faculty.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class FacultyService {

      /*
   Implement the business logic for the FacultyService  operations in this class
   Make sure to add required annotations
    */


    private FacultyRepository facultyRepository;


    private UserService userService;

    //to add the faculty using FacultyModel object
    //created->201
    //badRequest->400
    public ResponseEntity<Object> postFaculty(FacultyModel facultyModel) {
        return null;
    }


    //to get the faculty details based on the given request parameter and value
    //ok->200
    //badRequest->400
    public ResponseEntity<Object> getFaculty(String department, Integer experience, String gender) {
        return null;
    }

    //to update the faculty's experience, department and qualification
    //ok->200
    //badRequest->400
    public ResponseEntity<Object> updateFaculty(int id, FacultyModel faculty) {

        return null;
    }

    //to delete the faculty by using id
    //only the creater of that particular faculty can delete
    //noContent->204
    //badRequest->400
    //forbidden->403
    public ResponseEntity<Object> deleteFaculty(int id) {
        return null;
    }

}
