package com.customer.info.corptech.controller;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.customer.info.corptech.entities.Customer;
import com.customer.info.corptech.service.CustomerService;
import com.customer.info.corptech.service.SalesForceDataService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "customer", description = "Customer related API's")
@RestController
@RequestMapping("/api")
public class CustomerController {
	@Autowired
	CustomerService customerService;
	@Autowired
	SalesForceDataService salesForceDataService;

	@Operation(summary = "Creates a customer", description = "Creates a new customer and displays the same information on calling this API")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Succesfully created new customer") })
	@PostMapping(path = "/customers")
	public Customer createCustomer(@RequestBody Customer customer) {
		return customerService.createCustomer(customer);
	}

	@Operation(summary = "Fetches customer details", description = "Retrieves details of customer based on provided id and displays same to the user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful retrieved customer details") })
	@GetMapping("/customers/{id}")
	public Customer getCustomerDetails(@PathVariable("id") Long id) throws NoSuchElementException {
		return customerService.getCustomerDetails(id);
	}

	@Operation(summary = "Deletes the customer details", description = "Successfully deleted customer details based on provided id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully deleted the customer details provided by ID") })
	@DeleteMapping("/customers/{id}")
	@ResponseBody
	public void deleteCustomerById(@PathVariable("id") Long id) {
		if (customerService.getCustomerDetails(id) != null ) {
		customerService.deleteCustomerById(id);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Operation(summary = "Fetches details of all customers", description = "Retrieves information of all the custsomers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved details of all customers") })
	@GetMapping(path = "/customers")
	public Iterable<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	@Operation(summary = "Updates Customer details", description = "Updates the details of customer based on customer ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully updated customer details based on Customer ID") })
	@PutMapping(path = "/customers/{id}")
	public Customer updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
		if (customerService.getCustomerDetails(id) !=null && Objects.equals(id, customer.getCustomerId())) {
			return customerService.updateCustomer(customer);
		} else {
			throw new NoSuchElementException();
		}
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> NoValuePresent() {
		return new ResponseEntity<Object>("No Value Present", new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
}
