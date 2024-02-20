package com.customer.info.corptech.controller.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.customer.info.corptech.entities.Customer;
import com.customer.info.corptech.service.CustomerService;
import com.customer.info.corptech.service.SalesForceDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
	@MockBean
	CustomerService customerService;
	@MockBean
	SalesForceDataService salesForceDataService;
	@Autowired(required = true)
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	@Mock
	Optional<Customer> customerOpt;

	@Test
	public void testAddCustomer_WhenAccessGranted() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		Customer customer = new Customer();
		customer.setEmail("vvrk@gmail.com");
		customer.setName("ravi");
		customer.setPhoneNo(9581912301L);
		when(customerService.createCustomer(any())).thenReturn(customer);
		String customerJson = new ObjectMapper().writeValueAsString(customer);
		mockMvc.perform(post("/api/customers").accept(MediaType.APPLICATION_JSON).content(customerJson)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testAddCustomer_WhenAccessDenied() throws Exception {
		Customer customer = new Customer();
		customer.setEmail("vvrk@gmail.com");
		customer.setName("ravi");
		customer.setPhoneNo(9581912301L);
		when(customerService.createCustomer(any())).thenReturn(customer);
		String customerJson = new ObjectMapper().writeValueAsString(customer);
		mockMvc.perform(post("/api/customers").accept(MediaType.APPLICATION_JSON).content(customerJson)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}

	@Test
	public void testGetCustomerDetails_WithValidInput() throws Exception {
		Customer customer = new Customer();
		customer.setEmail("vvrk@gmail.com");
		customer.setName("ravi");
		customer.setPhoneNo(9581912301L);
		customer.setCustomerId(1);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		when(customerService.getCustomerDetails(anyLong())).thenReturn(customer);
		mockMvc.perform(get("/api/customers/{id}", 1)).andExpect(status().isOk());
	}

	@Test
	public void testGetCustomerDetails_WithInValidInput() throws Exception {
		Customer customer = new Customer();
		customer.setEmail("vvrk@gmail.com");
		customer.setName("ravi");
		customer.setPhoneNo(9581912301L);
		customer.setCustomerId(1);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		when(customerService.getCustomerDetails(anyLong())).thenThrow(NoSuchElementException.class);
		mockMvc.perform(get("/api/customers/{id}", 1))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchElementException));
	}

	@Test
	public void testGetAllCustomers_WhenNoDataFound() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		when(customerService.getAllCustomers()).thenReturn(new ArrayList<Customer>());
		mockMvc.perform(get("/api/customers")).andExpect(jsonPath("$", Matchers.hasSize(0))).andExpect(status().isOk());
	}

	@Test
	public void testGetAllCustomers() throws Exception {
		List<Customer> customers = new ArrayList<>();
		Customer customer = new Customer();
		customer.setEmail("vvrk@gmail.com");
		customer.setName("ravi");
		customer.setPhoneNo(9581912301L);
		customer.setCustomerId(1);
		customers.add(customer);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		when(customerService.getAllCustomers()).thenReturn(customers);
		mockMvc.perform(get("/api/customers")).andExpect(jsonPath("$", Matchers.hasSize(1))).andExpect(status().isOk());
	}

	@Test
	public void testDeleteCustomerById_WithInValidData() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		doThrow(NoSuchElementException.class).when(customerService).deleteCustomerById(anyLong());
		mockMvc.perform(delete("/api/customers/{id}", 1))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchElementException));
	}

	@Test
	public void testDeleteCustomerById_WithValidData() throws Exception {
		long customerId = 1L;
		Customer customer = new Customer();
		customer.setEmail("vvrk@gmail.com");
		customer.setName("ravi");
		customer.setPhoneNo(9581912301L);
		customer.setCustomerId(1L);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		when(customerService.getCustomerDetails(anyLong())).thenReturn(customer);
		when(customerOpt.isPresent()).thenReturn(true);
		doNothing().when(customerService).deleteCustomerById(customerId);
		ResultActions resultActions = mockMvc.perform(delete("/api/customers/{id}", customerId));
		resultActions.andExpect(status().isOk());
	}

	@Test
	public void testupdateCustomer() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		Customer customer = new Customer();
		customer.setEmail("vvrk@gmail.com");
		customer.setName("ravi");
		customer.setPhoneNo(9581912301L);
		customer.setCustomerId(1L);
		when(customerService.updateCustomer(any())).thenReturn(customer);
		String jsonCustomer = new ObjectMapper().writeValueAsString(customer);
		when(customerService.getCustomerDetails(anyLong())).thenReturn(customer);
		mockMvc.perform(put("/api/customers/{id}", 1L).accept(MediaType.APPLICATION_JSON).content(jsonCustomer)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void testupdateCustomer_WithInvalidData() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		Customer customer = new Customer();
		customer.setEmail("vvrk@gmail.com");
		customer.setName("ravi");
		customer.setPhoneNo(9581912301L);
		customer.setCustomerId(1L);
		when(customerService.updateCustomer(any())).thenThrow(NoSuchElementException.class);
		String jsonCustomer = new ObjectMapper().writeValueAsString(customer);
		when(customerService.getCustomerDetails(anyLong())).thenReturn(null);
		mockMvc.perform(put("/api/customers/{id}", 1L).accept(MediaType.APPLICATION_JSON).content(jsonCustomer)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}
}
