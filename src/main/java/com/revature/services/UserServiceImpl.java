package com.revature.services;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.models.Ticket;
import com.revature.models.User;

public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private static UserDAO userDAO = new UserDAOImpl();
		
	
	@Override
	public boolean registerUser(User user) {
		
		//1. log event
		logger.info("UserServiceImpl::register() called. Creating new user...");
		
		//2. get the new id number (do dao method call here)
		int id = userDAO.createUser(user);
		
		//3. return true if id exists else return false
		logger.info("Received from DAO. New ID: " + id);
		
		return (id != 0) ? true : false;
	}

	@Override
	public User getUserByUsername(String username) {
		logger.info("UserService::getUserByUsername() called. Trying to find user "+ username +"...");
		return userDAO.getByUsername(username);
	}
	
	@Override
	public boolean login(String username, String password) {
		logger.info("UserService::login() called. Trying to login user "+ username +"...");
		User target = userDAO.getByUsername(username);
		logger.info(target.getUsername());
		
		if(target.getUsername().equalsIgnoreCase(username) && target.getPassword().equalsIgnoreCase(password)) {
			return true;
		}
		//false = no user found, error in login
		return false;
	}


	
}
