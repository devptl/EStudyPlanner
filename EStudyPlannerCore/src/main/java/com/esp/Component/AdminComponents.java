package com.esp.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.model.Admin;
import com.esp.repository.AdminRepository;

@Service
public class AdminComponents {
	
	@Autowired
	private AdminRepository adminRepository;
	
	public Admin findOneAdmin(String userName)
	{
		return adminRepository.findOne(userName);
	}

}
