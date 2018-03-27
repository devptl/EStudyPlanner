package com.esp.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esp.model.Admin;
import com.esp.repository.AdminRepository;

@Component
public class AdminComponents {

	@Autowired
	private AdminRepository adminRepository;

	/**
	 * To find the admin by username
	 * 
	 * @param userName
	 *            - Admin UserName
	 * @return
	 */
	public Admin findOneAdmin(String userName) {
		return adminRepository.findOne(userName);
	}

}
