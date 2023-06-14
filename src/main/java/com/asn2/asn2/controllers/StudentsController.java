package com.asn2.asn2.controllers;

// import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asn2.asn2.models.Student;
import com.asn2.asn2.models.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class StudentsController {  
    
    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/students/view")
    public String getAllUsers(Model model){
        // System.out.println("Getting all students");
        // get all users from database
        List<Student> students = studentRepo.findAll();
        // end of database call
        model.addAttribute("st", students);
        return "students/showAll";
    }

    @GetMapping("/add")
    public String addNewUser(Model model){
        // System.out.println("Getting all students");
        return "redirect:add.html";
    }

    @PostMapping("/students/add")
    public String addUser(@RequestParam Map<String, String> newStudent, HttpServletResponse response){
        System.out.println("ADD student");
        // String newName = newuser.get("name");
        // String newPwd = newuser.get("password");
        // int newSize = Integer.parseInt(newuser.get("size"));

        String newName = newStudent.get("name"); 
        int newWeight = Integer.parseInt(newStudent.get("weight"));
        int newHeight = Integer.parseInt(newStudent.get("height"));
        String newHairColor = newStudent.get("hairColor"); 

        int newGPA = Math.round(10 * Float.parseFloat(newStudent.get("GPA")));
        // int newGPA = Integer.parseInt(newStudent.get("GPA"));
        // float newGPA = Float.parseFloat(newStudent.get("GPA"));
        
        String newMajor = newStudent.get("major");

        studentRepo.save(new Student(newName,newWeight,newHeight,newHairColor,newGPA,newMajor));
        response.setStatus(201); // object created
        // return "students/addedStudent";
        return "redirect:view";
    }

    @PostMapping("/students/update")
    public String editUser(@RequestParam Map<String, String> newStudent, HttpServletResponse response){
        // TODO: update given user entry in db
        System.out.println("Trying to update user...");

        // studentRepo.
        return "redirect:view";
    }

    @PostMapping("/students/delete")
    public String deleteUser(@RequestParam Map<String, String> newStudent, HttpServletResponse response){
        // TODO: update given user entry in db
        System.out.println("Trying to delete user...");


        // List<Student> students = studentRepo.findAll();
        List<Student> students = studentRepo.findByName("Dave");

        studentRepo.delete(students.get(0));

        // studentRepo.
        return "redirect:view";
    }
}