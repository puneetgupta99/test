package com.fresco.faculty.repository;

import com.fresco.faculty.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RoleRepository extends JpaRepository<RoleModel,Integer> {

    //Add the required annotations to make the RoleRepository
}
