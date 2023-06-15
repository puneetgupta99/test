package com.fresco.faculty.repository;

import com.fresco.faculty.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<UserModel,Integer> {

    //Add the required annotations to make the UserRepository
}
