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

    // @PostMapping("/students/edit")
    // public String editUser(Model model){
    //     // System.out.println("Getting all students");
    //     return "redirect:add.html";
    // }

    @PostMapping("/students/add")
    public String addUser(@RequestParam Map<String, String> newStudent, HttpServletResponse response){
        System.out.println("ADD student");

        String newName = newStudent.get("name"); 
        int newWeight = Integer.parseInt(newStudent.get("weight"));
        int newHeight = Integer.parseInt(newStudent.get("height"));
        String newHairColor = newStudent.get("hairColor"); 
        int newGPA = Math.round(10 * Float.parseFloat(newStudent.get("GPA")));
        String newMajor = newStudent.get("major");

        studentRepo.save(new Student(newName,newWeight,newHeight,newHairColor,newGPA,newMajor));
        response.setStatus(201); // object created
        // return "students/addedStudent";
        return "redirect:view";
    }

    @PostMapping("/students/edit")
    public String editUser(@RequestParam Map<String, String> newStudent, HttpServletResponse response, Model model){
        /* Serve an Edit view for the requested student uid */
        System.out.println("EDIT student");

        // String newName = newStudent.get("name"); 
        // int newWeight = Integer.parseInt(newStudent.get("weight"));
        // int newHeight = Integer.parseInt(newStudent.get("height"));
        // String newHairColor = newStudent.get("hairColor"); 
        // int newGPA = Math.round(10 * Float.parseFloat(newStudent.get("GPA")));
        // String newMajor = newStudent.get("major");

        // studentRepo.save(new Student(newName,newWeight,newHeight,newHairColor,newGPA,newMajor));
        // response.setStatus(201); // object created
        // return "redirect:view";

        List<Student> students = studentRepo.findByUid(Integer.parseInt(newStudent.get("uid")));
        Student student = students.get(0);

        System.out.println(student.getUid());
        
        model.addAttribute("st", student);
        // return "students/showAll";
        return "students/editStudent";
    }

    @PostMapping("/students/update")
    public String updateUser(@RequestParam Map<String, String> newStudent, HttpServletResponse response){
        // TODO: update given user entry in db
        
        System.out.println("Trying to update user...");
        System.out.println("at uid=" + newStudent.get("uid"));

        // List<Student> students = studentRepo.findByUid(Integer.parseInt(newStudent.get("uid")));
        // Student student = students.get(0);

        String newName = newStudent.get("name"); 
        int newWeight = Integer.parseInt(newStudent.get("weight"));
        int newHeight = Integer.parseInt(newStudent.get("height"));
        String newHairColor = newStudent.get("hairColor"); 
        int newGPA = Math.round(10 * Float.parseFloat(newStudent.get("GPA")));
        String newMajor = newStudent.get("major");

        System.out.println(newName);
        System.out.println(newWeight);
        System.out.println(newHeight);
        System.out.println(newGPA);
        System.out.println(newMajor);

        List<Student> students = studentRepo.findByUid(Integer.parseInt(newStudent.get("uid")));
        Student student = students.get(0);

        student.setName(newName);
        student.setWeight(newWeight);
        student.setHeight(newHeight);
        student.setHairColor(newHairColor);
        student.setGPA(newGPA);
        student.setMajor(newMajor);

        studentRepo.save(student);

        // System.out.println(student.getName());
        // System.out.println(student.getUid());

        // student.setName("Test 2");
        // System.out.println(student.getName());

        // studentRepo.save(new Student(newName,newWeight,newHeight,newHairColor,newGPA,newMajor));

        // System.out.println(student.getUid());

        return "redirect:view";
    }

    @PostMapping("/students/delete")
    public String deleteUser(@RequestParam Map<String, String> newStudent, HttpServletResponse response){
        // Delete given user entry in db
        // System.out.println("Trying to delete user...");
        // System.out.println("at uid=" + newStudent.get("uid"));

        List<Student> students = studentRepo.findByUid(Integer.parseInt(newStudent.get("uid")));
        studentRepo.delete(students.get(0));
        
        // return "students/showAll";
        return "redirect:view";
    }
}