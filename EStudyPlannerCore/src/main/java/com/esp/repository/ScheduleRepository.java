package com.esp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esp.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,String>{

}
