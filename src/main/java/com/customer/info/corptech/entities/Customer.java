package com.customer.info.corptech.entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {
	@Id
	private long customerId;
	private String name;
	private String email;
	private long phoneNo;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId, email, name, phoneNo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return customerId == other.customerId && Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& phoneNo == other.phoneNo;
	}

	public Customer(long customerId, String name, String email, long phoneNo) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.phoneNo = phoneNo;
	}
	
	public Customer() {
		
	}

}
