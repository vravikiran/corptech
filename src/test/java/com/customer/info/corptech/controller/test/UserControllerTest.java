package com.customer.info.corptech.controller.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.customer.info.corptech.entities.AuthRequest;
import com.customer.info.corptech.entities.UserInfo;
import com.customer.info.corptech.service.JwtService;
import com.customer.info.corptech.service.UserInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthenticationManager authenticationManager;
	@MockBean
	private JwtService jwtService;
	@MockBean
	private UserInfoService userInfoService;
	@Autowired
	private WebApplicationContext context;
	@MockBean
	Authentication authentication;

	public void setup() {

	}

	@Test
	public void TestAddUser() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail("rrvikr@gmail.com");
		userInfo.setRoles("ADMIN");
		userInfo.setId(1);
		userInfo.setName("Ravi");
		userInfo.setPassword("testPassword");
		String userInfoJson = new ObjectMapper().writeValueAsString(userInfo);
		when(userInfoService.addUserName(any())).thenReturn(userInfo);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/addUser").accept(MediaType.APPLICATION_JSON)
				.content(userInfoJson).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(userInfo.getId()))
				.andExpect(jsonPath("$.email").value(userInfo.getEmail()))
				.andExpect(jsonPath("$.password").value(userInfo.getPassword()))
				.andExpect(jsonPath("$.roles").value(userInfo.getRoles()))
				.andExpect(jsonPath("$.name").value(userInfo.getName()));
	}

	@Test
	public void TestAddUser_When_UserInfoMissing() throws Exception {
		String userInfoJson = "{\"id\":\" 1\", \"email\":\"vvrk@gmail.com\",\"password\":\"testPassword\",\"roles\":\"ADMIN,USER\"}";
		when(userInfoService.addUserName(any())).thenThrow(DataIntegrityViolationException.class);
		mockMvc.perform(post("/auth/addUser").content(userInfoJson).header(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andExpect(
						result -> assertTrue(result.getResolvedException() instanceof DataIntegrityViolationException));
	}

	@Test
	public void testGenerateToken_WhenValidUser() throws Exception {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("userName");
		authRequest.setPassword("password");
		String authReqJson = new ObjectMapper().writeValueAsString(authRequest);
		when(authenticationManager.authenticate(any())).thenReturn(authentication);
		when(authentication.isAuthenticated()).thenReturn(true);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		mockMvc.perform(post("/auth/generateToken").content(authReqJson).header(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testGenerateToken_WhenInValidUser() throws Exception {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("userName");
		authRequest.setPassword("password");
		String authReqJson = new ObjectMapper().writeValueAsString(authRequest);
		when(authenticationManager.authenticate(any())).thenReturn(authentication);
		when(authentication.isAuthenticated()).thenReturn(false);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		mockMvc.perform(post("/auth/generateToken").content(authReqJson).header(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof UsernameNotFoundException))
				.andExpect(status().isForbidden());
	}

}
