package com.revature.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.revature.models.Ticket;

public interface TicketDAO {

	//TICKET DAO
	//create (e), read (e) , update (m)
	
	int createTicket (Ticket ticket);
	
	Ticket getTicketByEmployeeId(int employeeId);
	
	Ticket getTicketById(int id);
	
	boolean updateTicket (Ticket ticket);

	HashMap<Integer, String > getPreviousTicketIds(int employeeId, int status);
	
	boolean doesTicketExist(int ticketId);

	ArrayList<String> managerViewTickets();
	
	ArrayList<String> managerViewTickets(int status);
}
