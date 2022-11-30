package com.revature.services;

import com.revature.models.Ticket;
import com.revature.models.User;

public interface UserService {

	public boolean registerUser(User user); 
	
	public User getUserByUsername(String username);
	
	public boolean login(String username, String password);
	
	public boolean registerTicket(Ticket ticket); 
	
	public Ticket getTicketByEmployeeId(int employeeId);
	
	public Ticket getTicketById(int id);
	
	public boolean updateTicket(Ticket ticket);
	
}
