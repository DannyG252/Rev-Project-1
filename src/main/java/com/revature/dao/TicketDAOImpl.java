package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Ticket;
import com.revature.util.JDBCConnectionUtil;

public class TicketDAOImpl implements TicketDAO{

	private static Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	@Override
	public int createTicket(Ticket ticket) {
	
		try {
			Connection conn = JDBCConnectionUtil.getConnection();
			
			String sql = "INSERT INTO tickets (employee_id, amount, description, status, manager_id, processed) VALUES( ?, ?, ?, 1, null, false)";
		
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			//pstmt.setInt(1, ticket.getId());
			pstmt.setInt(1, ticket.getEmployeeId());
			pstmt.setDouble(2, ticket.getAmount());
			pstmt.setString(3, ticket.getDescription());
			//pstmt.setInt(5, ticket.getStatus());
			//pstmt.setInt(6, ticket.getManagerId());
			//pstmt.setBoolean(7, ticket.isProcessed());
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			
			logger.info("TicketDAOImpl - createTicket() - new ticket id is " + rs.getInt(1));
			conn.close();
			return rs.getInt("id");
			
		
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return 0;
		}


	@Override
	public Ticket getTicketByEmployeeId(int employeeId) {
		try {
			Connection conn = JDBCConnectionUtil.getConnection();
			
			String sql = "SELECT * FROM tickets WHERE employee_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, employeeId);
		
			ResultSet rs = pstmt.executeQuery();
			Ticket currentTicket = new Ticket();
			
			while(rs.next()) {
				currentTicket.setId(rs.getInt("id"));
				currentTicket.setEmployeeId(rs.getInt("employee_id"));
				currentTicket.setAmount(rs.getDouble("amount"));
				currentTicket.setDescription(rs.getString("description"));
				currentTicket.setStatus(rs.getInt("status"));
				currentTicket.setManagerId(rs.getInt("manager_id"));
				currentTicket.setProcessed(rs.getBoolean("processed"));
			}
			
			logger.info("TicketDAOImpl - getTicketByEmployeeId - found ticket id is: " + rs.getInt("id"));
			conn.close();
			return currentTicket;
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		//if nothing found
		logger.info("Ticket not found");
		return null;
	}


	@Override
	public boolean updateTicket(Ticket ticket) {
			try {
				Connection conn = JDBCConnectionUtil.getConnection();
				
				//if (ticket.isProcessed() != true && ticket.getStatus() == 1) { //can only update if not already updated
				String sql = "UPDATE tickets SET status =?, manager_id = ?, processed = true WHERE id=?";
				PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1,ticket.getStatus());
				pstmt.setInt(2,ticket.getManagerId());
				pstmt.setInt(3,ticket.getId());
				
				if(pstmt.executeUpdate() > 0) {
					return true;
				};
			} catch (SQLException sqlEx){
				logger.error("TicketDAOImpl - updateTicket() " + sqlEx.getMessage());
			}
		return false;
	}


	@Override
	public Ticket getTicketById(int id) {
		try {
			Connection conn = JDBCConnectionUtil.getConnection();
			
			String sql = "SELECT id, employee_id, amount, description, status, processed FROM tickets WHERE id = ?";
			
			Ticket currentTicket = new Ticket();
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
		
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				currentTicket.setId(rs.getInt("id"));
				currentTicket.setEmployeeId(rs.getInt("employee_id"));
				currentTicket.setAmount(rs.getDouble("amount"));
				currentTicket.setDescription(rs.getString("description"));
				currentTicket.setStatus(rs.getInt("status"));
				currentTicket.setProcessed(rs.getBoolean("processed"));
			}
			
			logger.info("TicketDAOImpl - getTicketById - found ticket: " + id);
			conn.close();
			return currentTicket;
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		//if nothing found
		logger.info("Ticket not found");
		return null;
	}


	@Override
	public ArrayList<Integer> getPreviousTicketIds(int employeeId) {
			try {
			Connection conn = JDBCConnectionUtil.getConnection();
			
			String sql = "SELECT id FROM tickets WHERE employee_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, employeeId);
		
			ResultSet rs = pstmt.executeQuery();
			ArrayList<Integer> ticketIdList = new ArrayList<Integer>();
			
			while(rs.next()) {
				ticketIdList.add(rs.getInt("id"));
			}
			logger.info("TicketDAOImpl - getPreviousTickerIds - found ticket for employee " + employeeId);
			conn.close();
			return ticketIdList;
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		//if nothing found
		logger.info("Ticket not found");
		return null;
	}

	public boolean doesTicketExist (int ticketId) {
		try {
			Connection conn = JDBCConnectionUtil.getConnection();
			
			String sql = "SELECT id FROM tickets";
			
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	
			ResultSet rs = pstmt.executeQuery();
			ArrayList<Integer> ticketIdList = new ArrayList<Integer>();
			
			while(rs.next()) {
				ticketIdList.add(rs.getInt("id"));
			}
			
			logger.info("arraylist size: " + ticketIdList.size());
			if (ticketIdList.size() <= (ticketId-1)) {
				return true;
			}else {
				return false;
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		//if nothing found
		logger.info("Ticket not found");
		return false;
	}
	
}
