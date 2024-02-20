package com.customer.info.corptech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.customer.info.corptech.entities.AuthRequest;
import com.customer.info.corptech.entities.UserInfo;
import com.customer.info.corptech.service.JwtService;
import com.customer.info.corptech.service.UserInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "User Authentication and Authorization", description = "Supports user validation and generates token to authenticate other api's")
public class UserController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserInfoService userInfoService;

	@PostMapping(value = "/addUser", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public UserInfo addUser(@RequestBody UserInfo userInfo) {
		return userInfoService.addUserName(userInfo);
	}

	@PostMapping("/generateToken")
	@Operation(summary = "Generates Toekn", description = "Generates token based on provided user details")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully generated token") })
	public String AuthenticateAndGetToken(@RequestBody AuthRequest authRequest) throws UsernameNotFoundException {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("Invalid User");
		}
	}

	@ExceptionHandler({ UsernameNotFoundException.class })
	public ResponseEntity<Object> HandleUserNameNotFoundException() {
		return new ResponseEntity<Object>("Invalid User", new HttpHeaders(), HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> HandleInvalidDataException() {
		return new ResponseEntity<Object>("Invalid Data", new HttpHeaders(), HttpStatus.FORBIDDEN);
	}
}
