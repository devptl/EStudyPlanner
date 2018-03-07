package com.esp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esp.model.Students;

@Repository
public interface StudentsRepository extends JpaRepository<Students,String>{

}
