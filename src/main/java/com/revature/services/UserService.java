package com.revature.services;

import java.util.ArrayList;

import com.revature.models.Ticket;
import com.revature.models.User;

public interface UserService {

	public boolean registerUser(User user); 
	
	public User getUserByUsername(String username);
	
	public boolean login(String username, String password);
	
	public boolean updateUserRole(User user);
	}
