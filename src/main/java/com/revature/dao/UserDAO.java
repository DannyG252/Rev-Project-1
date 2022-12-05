package com.revature.dao;

import com.revature.models.User;

public interface UserDAO {

	int createUser(User user);
	
	User getByUsername(String username); 

	boolean updateUserRole(User user);
}

