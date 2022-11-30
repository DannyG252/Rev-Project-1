package com.revature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controllers.UserController;
import com.revature.services.UserService;

import io.javalin.Javalin;

public class MainDriver {

	public static Logger logger = LoggerFactory.getLogger(MainDriver.class);
	
	public static void main(String[] args) {
		
		Javalin app = Javalin.create().start(9000);
		
		//BEFORE & AFTER HANDLERS FOR EACH ENDPOINT CALL
		app.before(ctx -> {
			logger.info("Request at URL " + ctx.url() + " has started.");
		});

		app.after(ctx -> {
			logger.info("Request at URL " + ctx.url() + " is now complete.");
		});

		//GET METHODS
		app.get("/test", ctx -> {
			logger.info("Starting application...");
			ctx.html("Welcome to the application!");
		});
		

		
		//POST METHODS
		app.post("/users/register", UserController.register);
		app.post("/users/login", UserController.login);
		app.post("/tickets/register", UserController.registerTicket);
		app.post("/tickets/get-by-ticket-id", UserController.getTicketById);
		
		app.post("/tickets/view-previous", UserController.getPreviousTicketIds);
		
		//PUT METHODS
		
		app.put("/tickets/process", UserController.processTicket);
		
		
		
		
		
		
		
		
		/*app.get("/users/{id}", UserController.getUserById);

		//POST METHODS
		 * 
		 * +GET : view ticket (e)
		 * create user, login, submit ticket
		 * 
		app.post("/users/register", UserController.register);
		app.post("/users/login", UserController.login);

		//PUT METHODS
		 * 
		 * process ticket
		 * 
		app.put("/users/{id}", UserController.update);

		//DELETE METHODS
		 * 
		 * none
		 * 
		app.delete("/users/{id}", UserController.delete);
		}*/
	}

}

