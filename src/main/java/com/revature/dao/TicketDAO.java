package com.revature.dao;

import java.util.ArrayList;

import com.revature.models.Ticket;

public interface TicketDAO {

	//TICKET DAO
	//create (e), read (e) , update (m)
	
	int createTicket (Ticket ticket);
	
	Ticket getTicketByEmployeeId(int employeeId);
	
	Ticket getTicketById(int id);
	
	boolean updateTicket (Ticket ticket);

	ArrayList<Integer> getPreviousTicketIds(int employeeId);
	
	boolean doesTicketExist(int ticketId);
}
