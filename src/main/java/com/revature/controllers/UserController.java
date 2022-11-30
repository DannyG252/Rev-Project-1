package com.revature.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.models.LoginTemplate;
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	private static UserService uServ = new UserServiceImpl();
	
	//here we will need to set up a HTTP handler that deals solely for registration requests
	public static Handler register = ctx -> {
		
		//1. log event
		logger.info("User is making a registration request...");
		
		//2. get the user info from the request body
		String body = ctx.body();
		
		//here we will convert the body into a User object
		ObjectMapper om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		User target = om.readValue(body, User.class);
		logger.info("New User: " + target);
		
		//3. do service call
		boolean isCreated = uServ.registerUser(target);
		
		//4. render the response
		if(isCreated == true) {
			ctx.html("The new user has been created successfully.");
			ctx.status(HttpStatus.CREATED);
		}else {
			ctx.html("Error during creation. Try again.");
			ctx.status(HttpStatus.NO_CONTENT);
		}
	};
	
	public static Handler login = ctx -> {
		//1. get user info from request body
		String body = ctx.body();
		
		//here we will need to convert the body into a User object
		ObjectMapper om = new ObjectMapper();
		LoginTemplate target = om.readValue(body, LoginTemplate.class);
		
		//2. do service call
		boolean isAuthenicated = uServ.login(target.getUsername(), target.getPassword());
		
		//3. render response
		if(isAuthenicated == true) {
			ctx.html("Successful login. Welcome " + target.getUsername() + "!");
			ctx.status(HttpStatus.OK);
		}else {
			ctx.html("Invalid username and/or password. Please try again.");
			ctx.status(HttpStatus.UNAUTHORIZED);
		}
	};
	
	public static Handler registerTicket = ctx -> {
		logger.info("User is making a ticket registration request...");
		String body = ctx.body();
		
		//here we will convert the body into a User object
			ObjectMapper om = new ObjectMapper();
			Ticket target = om.readValue(body, Ticket.class);
			logger.info("New Ticket: " + target);
			
			boolean isCreated = uServ.registerTicket(target);
			
			if(isCreated == true) {
				ctx.html("The new ticket has been created successfully.");
				ctx.status(HttpStatus.CREATED);
			}else {
				ctx.html("Error during creation. Try again.");
				ctx.status(HttpStatus.NO_CONTENT);
			}
	};
	
	
	public static Handler processTicket = ctx -> {
		logger.info("User is making a ticket processing request...");
		String body = ctx.body();
		
		//here we will convert the body into a User object
		ObjectMapper om = new ObjectMapper();
		Ticket target = om.readValue(body, Ticket.class);
		
		boolean isUpdated = uServ.updateTicket(target);
		
		if(isUpdated == true) {
			ctx.html("Ticket ID# "+ target.getId() +"has been processed successfully.");
			ctx.status(HttpStatus.OK);
		}else {
			ctx.html("Error during processing. Try again.");
			ctx.status(HttpStatus.BAD_REQUEST);
		}
		
	};


}
