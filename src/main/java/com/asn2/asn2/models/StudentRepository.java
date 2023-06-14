package com.asn2.asn2.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    List<Student> findByUid(int uid);
    List<Student> findByHeight(int height);
    List<Student> findByWeight(int weight);
    List<Student> findByName(String name);
}