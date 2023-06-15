package com.fresco.faculty.repository;

import com.fresco.faculty.model.FacultyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface FacultyRepository extends JpaRepository<FacultyModel, Integer> {

    //Add the required annotations to make the FacultyRepository
}
