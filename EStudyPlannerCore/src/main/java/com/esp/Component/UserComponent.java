package com.esp.Component;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esp.model.Users;
import com.esp.repository.UserRepository;

/**
 * Component class to provide components on users
 * findOne()                                  -  To find one user
 * findAll()                                  -  To find all user
 * saveUser()                                 -  To save a particular user
 * 
 * @author mindfire
 *
 */
@Component
public class UserComponent {

	@Autowired
	private UserRepository userRepository;

	/**
	 * To find one user
	 * 
	 * @param userName
	 * @return
	 */
	public Users findOne(String userName) {
		return userRepository.findOne(userName);
	}

	/**
	 * To find all user
	 * 
	 * @return
	 */
	public ArrayList<Users> findAll() {
		return (ArrayList<Users>) userRepository.findAll();
	}

	/**
	 * To save a particular user
	 * 
	 * @param user
	 */
	public void saveUser(Users user) {
		userRepository.save(user);
	}

	public Users findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
