package com.revature.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.models.LoginTemplate;
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.services.TicketService;
import com.revature.services.TicketServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import jakarta.servlet.http.Cookie;

public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	private static UserService uServ = new UserServiceImpl();
	private static TicketService tServ = new TicketServiceImpl();
	
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
		
		//here we will need to convert the body into a LoginTemplate object
		ObjectMapper om = new ObjectMapper();
		LoginTemplate target = om.readValue(body, LoginTemplate.class);
		
		//2. do service call
		boolean isAuthenicated = uServ.login(target.getUsername(), target.getPassword());
		
		//3. render response
		if(isAuthenicated == true) {
			ctx.html("Successful" + "login. Welcome " + target.getUsername() + "!");
			
			//authorize user - cookie
			ctx.cookieStore().set("Auth-Cookie", target.getUsername() + "unique-key123");
			Cookie auth = new Cookie ("Auth-Cookie", target.getUsername() + "cookie-end567");
			ctx.res().addCookie(auth);
					
			ctx.status(HttpStatus.OK);
		}else {
			ctx.html("Invalid username and/or password. Please try again.");
			ctx.status(HttpStatus.UNAUTHORIZED);
		}
	};
	
	public static Handler registerTicket = ctx -> {
		logger.info("User is making a ticket registration request...");
		String body = ctx.body();
		
		//here we will convert the body into a Ticket object
			ObjectMapper om = new ObjectMapper();
			Ticket target = om.readValue(body, Ticket.class);
			logger.info("New Ticket: " + target);
			
			//****
			//get HTTP cookie from the request header
			String cookieReq = ctx.cookieStore().get("Auth-Cookie");
			cookieReq = cookieReq.replaceAll("unique-key123","");
			logger.info("Authenication cookie: " + cookieReq);
			
			//get the logged in user info
			User currUser = uServ.getUserByUsername(cookieReq);
			logger.info("Based on cookie, current user is: " + currUser.toString());

			//if user matches the employee username, then allowed to perform func
			try {
				if(currUser.getRole() == 2) { // if employee is logged in
					boolean isCreated = tServ.registerTicket(target);
					
					if(isCreated == true) {
						ctx.html("The new ticket has been created successfully.");
						ctx.status(HttpStatus.CREATED);
					}else {
						ctx.html("Error during creation. Try again.");
						ctx.status(HttpStatus.NO_CONTENT);
					}
				}else {
					ctx.html("Sorry, this user is not authorized to perform this action");
					ctx.status(HttpStatus.UNAUTHORIZED);
				}
			}catch (NullPointerException e) {
				ctx.html("Sorry, this user is not authorized to perform this action");
				ctx.status(HttpStatus.UNAUTHORIZED);
			}
	};
	
	
	public static Handler processTicket = ctx -> {
		logger.info("User is making a ticket processing request...");
		String body = ctx.body();
		
		//here we will convert the body into a Ticket object
		ObjectMapper om = new ObjectMapper();
		Ticket target = om.readValue(body, Ticket.class);
		
		//**Check cookie if manager**
		String cookieReq = ctx.cookieStore().get("Auth-Cookie");
		cookieReq = cookieReq.replaceAll("unique-key123","");
		logger.info("Authenication cookie: " + cookieReq);
		User currUser = uServ.getUserByUsername(cookieReq);
		logger.info("Based on cookie, current user is: " + currUser.toString());
		try {
			if(currUser.getRole() == 1) { //if manager is logged in
	
		//if already processed, cant process again
		boolean alreadyProcessed = tServ.checkTicketProcessed(target.getId());
		if (alreadyProcessed) {
			ctx.html("This ticket has already been processed.");
			ctx.status(HttpStatus.FORBIDDEN);
			}else {
				boolean isUpdated = tServ.updateTicket(target);
				
				if(isUpdated == true) {
					ctx.html("Ticket ID# "+ target.getId() +"has been processed successfully.");
					ctx.status(HttpStatus.OK);
				}else {
					ctx.html("Error during processing. Try again.");
					ctx.status(HttpStatus.BAD_REQUEST);
				}
			}
		}else {
			ctx.html("Sorry, this user is not authorized to perform this action");
			ctx.status(HttpStatus.UNAUTHORIZED);
		}
		}catch (NullPointerException e) {
			ctx.html("Sorry, this user is not authorized to perform this action");
			ctx.status(HttpStatus.UNAUTHORIZED);
		}
	};
	
	public static Handler getTicketById = ctx -> {
		logger.info("User is making a ticket-get-by-id request...");
		String body = ctx.body();
		
		//here we will convert the body into a Ticket object
		ObjectMapper om = new ObjectMapper();
		Ticket target = om.readValue(body, Ticket.class);
		
		boolean doesntExist = tServ.doesTicketExist(target.getId());
		
		Ticket currentTicket = tServ.getTicketById(target.getId());
		
		if( currentTicket != null && !doesntExist ) {
			ctx.html(currentTicket.toString());
			ctx.status(HttpStatus.OK);
		}else {
			ctx.html("Ticket doesn't exist or cannot be found.");
			ctx.status(HttpStatus.BAD_REQUEST);
		}
		
	};


	public static Handler getPreviousTicketIds = ctx -> {
		logger.info("User is making a ticket-view-previous request...");
		String body = ctx.body();
		
		//here we will convert the body into a Ticket object
			ObjectMapper om = new ObjectMapper();
			Ticket target = om.readValue(body, Ticket.class);

			HashMap<Integer, String> ticketIdList = tServ.getPreviousTicketIds(target.getEmployeeId(), target.getStatus());
			
			if( ticketIdList.size() > 0 ) {	
			Iterator<Map.Entry<Integer, String>> iterator = ticketIdList.entrySet().iterator();
			String printString = "Found tickets: \n";
			
			while(iterator.hasNext()) {
				Map.Entry<Integer, String> currTicket = iterator.next();
				printString += "Ticket Id = " + currTicket.getKey() + " , Status = " + currTicket.getValue() + "\n";
			}
				
				ctx.html(printString);
				ctx.status(HttpStatus.OK);
			}else {
				ctx.html("Error during retrieval. Try again.");
				ctx.status(HttpStatus.BAD_REQUEST);
			}
	};
}
