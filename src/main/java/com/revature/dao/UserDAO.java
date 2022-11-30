package com.revature.dao;


import java.util.ArrayList;

import com.revature.models.Ticket;
import com.revature.models.User;

public interface UserDAO {

	int createUser(User user);
	
	User getByUsername(String username); 
	
	
	//TICKET DAO
	//create (e), read (e) , update (m)
	
	int createTicket (Ticket ticket);
	
	//Ticket getById(int id);
	
	Ticket getTicketByEmployeeId(int employeeId);
	
	Ticket getTicketById(int id);
	
	boolean updateTicket (Ticket ticket);

	ArrayList<Integer> getPreviousTicketIds(int employeeId);
	

}

