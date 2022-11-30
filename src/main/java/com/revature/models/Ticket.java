package com.revature.models;

import java.util.Objects;

public class Ticket {

	
	private int id;
	private double amount;
	private String description;
	private int status; //1 pending, 2 approved, 3 denied
	private int employeeId;
	
	private int managerId;
	private boolean processed;
	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Ticket(int id, double amount, String description, int status, int employeeId, int managerId,
			boolean processed) {
		super();
		this.id = id;
		this.amount = amount;
		this.description = description;
		this.status = status;
		this.employeeId = employeeId;
		this.managerId = managerId;
		this.processed = processed;
	}
	public Ticket(double amount, String description, int status, int employeeId, int managerId, boolean processed) {
		super();
		this.amount = amount;
		this.description = description;
		this.status = status;
		this.employeeId = employeeId;
		this.managerId = managerId;
		this.processed = processed;
	}
	public Ticket(double amount, String description, int status, int employeeId, boolean processed) {
		super();
		this.amount = amount;
		this.description = description;
		this.status = status;
		this.employeeId = employeeId;
		this.processed = processed;
	}
	@Override
	public String toString() {
		
	String statusString = "";
		
		switch(status) {
		case 1: statusString = "pending"; break;
		case 2: statusString = "approved"; break;
		case 3: statusString = "denied"; break;
		}
		
		return "Ticket [id=" + id + ", amount=" + amount + ", description=" + description + ", status=" + statusString
				+ ", employeeId=" + employeeId + ", managerId=" + managerId + ", processed=" + processed + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(amount, description, employeeId, id, managerId, processed, status);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(description, other.description) && employeeId == other.employeeId && id == other.id
				&& managerId == other.managerId && processed == other.processed && status == other.status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public boolean isProcessed() {
		return processed;
	}
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	
	
	
	
}
