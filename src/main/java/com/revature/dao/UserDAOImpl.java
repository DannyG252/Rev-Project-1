package com.revature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.util.JDBCConnectionUtil;

public class UserDAOImpl implements UserDAO{

	static Connection conn = JDBCConnectionUtil.getConnection();
	
	private static Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	

	public int createUser(User user) {
		// this will be how we insert new users into db
		try {
			//1. prepare our SQL statement
			//we are using ? as a placeholder for the values we will set in our preparedstatement
			//this is used to prevent SQL injection (aka your users having the ability to mess up your code with their own data)
			String sql = "INSERT INTO users (username, password, role) VALUES(?, ?, ?)";
			
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			//now we are filling in the ?
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, Integer.valueOf(user.getRole()));
			
			//note that when we are inserting, we will get back our new ID number from the ResultSet
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			
			//this result set works off of a cursor that starts before the first record
			rs.next();
			
			logger.info("UserDAOImpl - create() - new user id is " + rs.getInt(1));
			conn.close();
			return rs.getInt("id");
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		//else if no user creation done, return 0
		return 0;
	}


	public User getByUsername(String username) {
		try {
			String sql = "SELECT id, username, password, role FROM users WHERE username = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, username);
		
			ResultSet rs = pstmt.executeQuery();
			User currentUser = new User();
			
			while(rs.next()) {
				currentUser.setId(rs.getInt("id"));
				currentUser.setUsername(rs.getString("username"));
				currentUser.setPassword(rs.getString("password"));
				currentUser.setRole(rs.getInt("role"));
			}
			
			
			logger.info("UserDAOImpl - getByUsername - found user is: " + username);
			conn.close();
			return currentUser;
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		//if nothing found
		logger.info("User not found");
		return null;
	}


	@Override
	public int createTicket(Ticket ticket) {
	
		try {
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
			
			logger.info("UserDAOImpl - createTicket() - new ticket id is " + rs.getInt(1));
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
			
			logger.info("UserDAOImpl - getTicketByEmployeeId - found ticket id is: " + rs.getInt("id"));
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
			logger.error("UserDAOImpl - updateTicket() " + sqlEx.getMessage());
		}
		return false;
	}


	@Override
	public Ticket getTicketById(int id) {
		try {
			String sql = "SELECT employee_id, amount, description, status FROM tickets WHERE id = ?";
			
			Ticket currentTicket = new Ticket();
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
		
			ResultSet rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
			
				currentTicket.setEmployeeId(rs.getInt("employee_id"));
				currentTicket.setAmount(rs.getDouble("amount"));
				currentTicket.setDescription(rs.getString("description"));
				currentTicket.setStatus(rs.getInt("status"));
			}
			
			logger.info("UserDAOImpl - getTicketById - found ticket: " + id);
			conn.close();
			return currentTicket;
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		//if nothing found
		logger.info("Ticket not found");
		return null;
	}

}
	
