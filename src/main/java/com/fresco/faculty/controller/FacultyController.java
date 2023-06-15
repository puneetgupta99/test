package com.fresco.faculty.controller;

import com.fresco.faculty.model.FacultyModel;
import com.fresco.faculty.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public class FacultyController {

      /*
  This controller would be responsible for the FacultyController endpoints
  Add the required annotations and create the endpoints
   */


    private FacultyService facultyService;

    //to create a faculty using FacultyModel object
    @PostMapping("/faculty/add")
    public ResponseEntity<Object> postFaculty(FacultyModel facultyModel){
        return null;
    }

    //to get the  faculty with the given param values
    @GetMapping("/faculty/list")
    public ResponseEntity<Object> getFaculty(String department,Integer experience,String gender){
        return null;
    }

    //to update the faculty's experience by id as PathVariable and faculty Object
    @PatchMapping("/faculty/update/{id}")
    public ResponseEntity<Object> updateFaculty(int id,FacultyModel faculty){
        return null;
    }

    // to delete the faculty by using id as PathVariable
    @DeleteMapping("/faculty/delete/{id}")
    public ResponseEntity<Object> deleteFaculty(int id){
        return null;
    }


}
