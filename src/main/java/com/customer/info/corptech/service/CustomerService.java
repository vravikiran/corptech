package com.customer.info.corptech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.info.corptech.dao.CustomerRepository;
import com.customer.info.corptech.entities.Customer;

@Service
public class CustomerService {
	@Autowired
	CustomerRepository customerRepository;
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer getCustomerDetails(Long customerId) {
		return customerRepository.findById(customerId).get();
	}

	public void deleteCustomerById(Long customerId) {
		customerRepository.deleteById(customerId);
	}

	public List<Customer> getAllCustomers() {
		return (List<Customer>) customerRepository.findAll();
	}

	public Customer updateCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
}
