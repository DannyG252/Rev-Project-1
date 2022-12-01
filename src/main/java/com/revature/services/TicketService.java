package com.revature.services;

import java.util.ArrayList;

import com.revature.models.Ticket;

public interface TicketService {


	public boolean registerTicket(Ticket ticket); 
	
	public Ticket getTicketByEmployeeId(int employeeId);
	
	public Ticket getTicketById(int id);
	
	public boolean updateTicket(Ticket ticket);

	public ArrayList<Integer> getPreviousTicketIds(int employeeId);

	public boolean checkTicketProcessed(int ticketId);
	
	public boolean doesTicketExist(int ticketId);
}
