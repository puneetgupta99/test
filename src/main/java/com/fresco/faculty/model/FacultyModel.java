package com.fresco.faculty.model;

import javax.persistence.*;

@Entity
public class FacultyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String faculty_name;
    private String qualification;
    @Column(unique = true)
    private String email;
    private String gender;
    private String department;
    private int experience;
    private int admin_id;

    //constructor


    public FacultyModel() {
    }

    public FacultyModel(int id, String faculty_name, String qualification, String email, String gender, String department, int experience, int admin_id) {
        this.id = id;
        this.faculty_name = faculty_name;
        this.qualification = qualification;
        this.email = email;
        this.gender = gender;
        this.department = department;
        this.experience = experience;
        this.admin_id = admin_id;
    }

    public FacultyModel(String qualification, String department, int experience) {
        this.qualification = qualification;
        this.department = department;
        this.experience = experience;
    }

    public FacultyModel(int experience) {
        this.experience = experience;
    }

    public FacultyModel(String faculty_name, String qualification, String email, String gender, String department, int experience) {
        this.faculty_name = faculty_name;
        this.qualification = qualification;
        this.email = email;
        this.gender = gender;
        this.department = department;
        this.experience = experience;
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }


    //to_string


    @Override
    public String toString() {
        return "FacultyModel{" +
                "id=" + id +
                ", faculty_name='" + faculty_name + '\'' +
                ", qualification='" + qualification + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", department='" + department + '\'' +
                ", experience=" + experience +
                ", admin_id=" + admin_id +
                '}';
    }
}
