package com.asn2.asn2.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    List<User> findByHeight(int height);
    List<User> findByWeight(int weight);
    // List<User> findByNameAndPassword(String name, String password);
}