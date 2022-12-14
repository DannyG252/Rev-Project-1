package com.revature.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.TicketDAO;
import com.revature.dao.TicketDAOImpl;
import com.revature.models.Ticket;

public class TicketServiceImpl implements TicketService{

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private static TicketDAO ticketDAO = new TicketDAOImpl();
	
	@Override
	public boolean registerTicket(Ticket ticket, int employeeId) {
		//1. log event
		logger.info("TicketServiceImpl::registerTicket() called. Creating new user...");
		
		//2. get the new id number (do dao method call here)
		int id = ticketDAO.createTicket(ticket, employeeId);
		
		//3. return true if id exists else return false
		logger.info("Received from DAO. New ID: " + id);
		
		return (id != 0) ? true : false;
	}

	@Override
	public Ticket getTicketByEmployeeId(int employeeId) {
		logger.info("TicketService::getTicketByEmployeeId() called. Trying to find tickets for employee "+ employeeId +"...");
		return ticketDAO.getTicketByEmployeeId(employeeId);
	}
	
	@Override
	public Ticket getTicketById(int id) {
		logger.info("TicketService::getTicketById() called. Trying to find ticket "+ id +"...");
		return ticketDAO.getTicketById(id);
	}

	@Override
	public boolean updateTicket(Ticket ticket, int managerId) {
		logger.info("TicketService::updateTicket() called. Updating ticket ID# "+ ticket.getId() +"...");
		return ticketDAO.updateTicket(ticket, managerId);
	}

	@Override
	public HashMap<Integer, String> getPreviousTicketIds(int employeeId, int status) {
		logger.info("TicketService::getPreviousTicketIds() called. Trying to find tickets for employee "+ employeeId +"...");
		return ticketDAO.getPreviousTicketIds(employeeId, status);
	}

	@Override
	public boolean checkTicketProcessed(int ticketId) {
		logger.info("TicketService::checkTicketProcessed() called. Checking ticket ID# "+ ticketId +"...");
		Ticket currentTicket = getTicketById(ticketId);
		
		logger.info("this ticket has already been proccessd t/f: " + currentTicket.isProcessed());
		return currentTicket.isProcessed();
	}
	
	public boolean doesTicketExist(int ticketId) {
		logger.info("TicketService::doesTicketExist() called. Trying to find ticket "+ ticketId +"...");
		return ticketDAO.doesTicketExist(ticketId);
	}

	@Override
	public ArrayList<String> managerViewTickets() {
		logger.info("TicketService::managerViewTickets() called. Trying to find tickets...");
		return ticketDAO.managerViewTickets();
	}

	@Override
	public ArrayList<String> managerViewTickets(int status) {
		logger.info("TicketService::managerViewTickets(itn status) called. Trying to find pending tickets...");
		return ticketDAO.managerViewTickets(status);
	}
	
	

}
