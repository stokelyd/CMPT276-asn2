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
        // get all users from database
        List<Student> students = studentRepo.findAll();

        model.addAttribute("st", students);
        return "students/showAll";
    }

    @GetMapping("/add")
    public String addNewUser(Model model){
        /* Serve our page for adding new Students */
        return "redirect:add.html";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam Map<String, String> newStudent, HttpServletResponse response){
        /* Adds a new student to our DB with the given values */
        String newName = newStudent.get("name"); 
        int newWeight = Integer.parseInt(newStudent.get("weight"));
        int newHeight = Integer.parseInt(newStudent.get("height"));
        String newHairColor = newStudent.get("hairColor"); 
        int newGPA = Math.round(10 * Float.parseFloat(newStudent.get("GPA")));
        String newMajor = newStudent.get("major");

        studentRepo.save(new Student(newName,newWeight,newHeight,newHairColor,newGPA,newMajor));
        response.setStatus(201); // object created

        return "redirect:view";
    }

    @PostMapping("/students/edit")
    public String editUser(@RequestParam Map<String, String> newStudent, HttpServletResponse response, Model model){
        /* Serve an Edit view for the requested student uid */
        List<Student> students = studentRepo.findByUid(Integer.parseInt(newStudent.get("uid")));
        Student student = students.get(0);
        
        // update our model with the info from our desired Student
        model.addAttribute("st", student);

        return "students/editStudent";
    }

    @PostMapping("/students/update")
    public String updateUser(@RequestParam Map<String, String> newStudent, HttpServletResponse response){
        /* Update a Student entry in our DB, given changes made */

        // get new values from form
        String newName = newStudent.get("name"); 
        int newWeight = Integer.parseInt(newStudent.get("weight"));
        int newHeight = Integer.parseInt(newStudent.get("height"));
        String newHairColor = newStudent.get("hairColor"); 
        int newGPA = Math.round(10 * Float.parseFloat(newStudent.get("GPA")));
        String newMajor = newStudent.get("major");

        // retrieve student by UID
        List<Student> students = studentRepo.findByUid(Integer.parseInt(newStudent.get("uid")));
        Student student = students.get(0);

        // set values of our existing student record
        student.setName(newName);
        student.setWeight(newWeight);
        student.setHeight(newHeight);
        student.setHairColor(newHairColor);
        student.setGPA(newGPA);
        student.setMajor(newMajor);

        // save modified student record
        studentRepo.save(student);

        return "redirect:view";
    }

    @PostMapping("/students/delete")
    public String deleteUser(@RequestParam Map<String, String> newStudent, HttpServletResponse response){
        /* Deletes a Student record with a given UID */
        List<Student> students = studentRepo.findByUid(Integer.parseInt(newStudent.get("uid")));
        studentRepo.delete(students.get(0));
        
        return "redirect:view";
    }
}