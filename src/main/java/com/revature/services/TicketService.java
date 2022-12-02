package com.revature.services;

import java.util.ArrayList;
import java.util.HashMap;

import com.revature.models.Ticket;

public interface TicketService {


	public boolean registerTicket(Ticket ticket); 
	
	public Ticket getTicketByEmployeeId(int employeeId);
	
	public Ticket getTicketById(int id);
	
	public boolean updateTicket(Ticket ticket);

	public HashMap<Integer, String> getPreviousTicketIds(int employeeId, int status);

	public boolean checkTicketProcessed(int ticketId);
	
	public boolean doesTicketExist(int ticketId);
	
	public ArrayList<String> managerViewTickets();
	
	public ArrayList<String> managerViewTickets(int status);
}
