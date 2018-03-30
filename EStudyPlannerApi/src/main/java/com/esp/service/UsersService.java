package com.esp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.Component.UserComponent;
import com.esp.model.LoggedUser;
import com.esp.model.Users;

@Service
public class UsersService {
	
	@Autowired
	private UserComponent userComponent;
	
	@Autowired
	private Encoder encoder;

	
	/**
	 * To check For the user by email
	 * @param userName
	 * @param email
	 * @return
	 */
	public boolean checkForUser(String userName, String email) {

		Users user = userComponent.findOne(userName);
		
		if (user == null) {
			return false;
		} else {
			// to find the experts with particualr username and email
			if (user.getEmail().equals(email)) {
				return true;
			}

			return false;
		}
	}
	
	/**
	 * For the user login
	 * @param l1 - {@link LoggedUser}
	 * @return loginStatus
	 */
	public boolean usersLogin(LoggedUser l1) {

		String userName = l1.getUserName();
		Users user = userComponent.findOne(userName);
		
		// checking if expert exist with username and password
		if ( user == null) {
			return false;
		} else {
			String encodedPassword = encoder.encodePassword(l1.getPassword());
			
			//checking the authentication of the user
			if (user.getPassword().equals(encodedPassword)) {
				
				return true;
			}

			return false;
		}

	}
	
	/**
	 * When a user want to change the password
	 * @param userName
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public boolean changeTheUserPassword(String userName, String oldPassword, String newPassword) {

		Users user = userComponent.findOne(userName);
		
		if ( user == null) {
			return false;
		} else {
			
			String encodedPassword = encoder.encodePassword(oldPassword);
			// checking that the expert exist
			if (user.getPassword().equals(encodedPassword)) {
				
				//encode the new password
				String newEncodedPassword = encoder.encodePassword(newPassword);
				
				// saving the new password
				user.setPassword(newEncodedPassword);
				userComponent.saveUser(user);

				return true;
			}

			return false;
		}

	}
	
	/**
	 * To check for the user
	 * @param userName
	 * @return
	 */
	public Users findUser(String userName) {
		return userComponent.findOne(userName);
	}

}
