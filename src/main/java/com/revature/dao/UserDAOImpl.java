package com.revature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.util.JDBCConnectionUtil;

public class UserDAOImpl implements UserDAO{
	
	private static Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	

	public int createUser(User user) {
		// this will be how we insert new users into db
		try {
			Connection conn = JDBCConnectionUtil.getConnection();
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
			Connection conn = JDBCConnectionUtil.getConnection();
			
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

}
	
