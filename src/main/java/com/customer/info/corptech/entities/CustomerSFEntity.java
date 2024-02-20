package com.customer.info.corptech.entities;

public class CustomerSFEntity {

	Attributes attributes;
	private Long customerId__c;
	private String name;
	private String email__c;
	private Long phoneNo__c;

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public Long getCustomerId__c() {
		return customerId__c;
	}

	public void setCustomerId__c(Long customerId__c) {
		this.customerId__c = customerId__c;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail__c() {
		return email__c;
	}

	public void setEmail__c(String email__c) {
		this.email__c = email__c;
	}

	public Long getPhoneNo__c() {
		return phoneNo__c;
	}

	public void setPhoneNo__c(Long phoneNo__c) {
		this.phoneNo__c = phoneNo__c;
	}

	public CustomerSFEntity(Attributes attributes, Long customerId__c, String name, String email__c, Long phoneNo__c) {
		super();
		this.attributes = attributes;
		this.customerId__c = customerId__c;
		this.name = name;
		this.email__c = email__c;
		this.phoneNo__c = phoneNo__c;
	}

}
