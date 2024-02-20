package com.customer.info.corptech.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.customer.info.corptech.dao.CustomerRepository;
import com.customer.info.corptech.entities.Customer;
import com.customer.info.corptech.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
	@Mock
	CustomerRepository customerRepository;
	@InjectMocks
	CustomerService customerService;

	@Test
	public void testCreateCustomer_WithvalidData() {
		Customer customer = buildCustomerInfo();
		given(customerRepository.save(any())).willReturn(customer);
		Customer resultCustsomer = customerService.createCustomer(any());
		assertThat(resultCustsomer).isNotNull();
	}

	@Test
	public void testCreateCustomer_WithInvalidData() {
		Customer customer = buildCustomerInfo();
		customer.setName(null);
		given(customerRepository.save(any())).willReturn(null);
		Customer customerResult = customerService.createCustomer(customer);
		assertThat(customerResult).isNull();
	}
	
	@Test
	public void testGetCustomer_WithValiData() {
		Customer customer = buildCustomerInfo();
		given(customerRepository.findById(anyLong())).willReturn(Optional.of(customer));
		Customer customerResult = customerService.getCustomerDetails(1L);
		assertEquals(customer, customerResult);
	}
	
	@Test
	public void testGetCustomer_WithInValiData() {
		given(customerRepository.findById(anyLong())).willThrow(NoSuchElementException.class);
		assertThrows(NoSuchElementException.class, ()->customerService.getCustomerDetails(1L));
	}

	@Test
	public void testUpdateCustomer_WithValidData() {
		Customer customer = buildCustomerInfo();
		given(customerRepository.save(any())).willReturn(customer);
		Customer customerResult = customerService.updateCustomer(customer);
		assertThat(customerResult).isNotNull();
	}

	@Test
	public void testUpdateCustomer_WithInValidData() {
		Customer customer = buildCustomerInfo();
		customer.setName(null);
		given(customerRepository.save(any())).willReturn(null);
		Customer customerResult = customerService.updateCustomer(customer);
		assertThat(customerResult).isNull();
	}

	@Test
	public void testGetAllCustomers_WithData() {
		List<Customer> customers = new ArrayList<>();
		Customer customer = buildCustomerInfo();
		customers.add(customer);
		given(customerRepository.findAll()).willReturn(customers);
		List<Customer> resultCustomers = customerService.getAllCustomers();
		assertTrue(customers.equals(resultCustomers));
	}

	@Test
	public void testGetAllCustomers_WithOutData() {
		given(customerRepository.findAll()).willReturn(new ArrayList<Customer>());
		List<Customer> resultCustomers = customerService.getAllCustomers();
		assertTrue(resultCustomers.isEmpty());
	}

	@Test
	public void testDeleteCustomerWithValidData() {
		doNothing().when(customerRepository).deleteById(1L);
		customerService.deleteCustomerById(1L);
		verify(customerRepository, times(1)).deleteById(1L);
	}

	@Test
	public void testDeleteCustomerWithInValidData() throws NoSuchElementException {
		doThrow(new NoSuchElementException()).when(customerRepository).deleteById(1L);
		assertThrows(NoSuchElementException.class, () -> customerService.deleteCustomerById(1L));
	}

	private Customer buildCustomerInfo() {
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setEmail("vvrk@gmail.com");
		customer.setName("Ravi");
		customer.setPhoneNo(9581912301L);
		return customer;
	}
}
