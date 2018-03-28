package com.esp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esp.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,String>{
	public Users findByEmail(String email);

}
