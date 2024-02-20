package com.customer.info.corptech.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.customer.info.corptech.entities.LoginResponse;

@Service
public class LoginOperation {
	@Value("${login.login_url}")
	private String login_url;

	@Value("${login.grant_type}")
	private String grant_type;

	@Value("${login.username}")
	private String username;

	@Value("${login.password}")
	private String password;

	@Value("${login.client_id}")
	private String client_id;

	@Value("${login.client_secret}")
	private String client_secret;

	public LoginResponse login() {
		ResponseEntity<LoginResponse> response = null;
		try {
			URI uri = new URI(login_url);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("username", username);
			params.add("password", password);
			params.add("client_secret", client_secret);
			params.add("client_id", client_id);
			params.add("grant_type", grant_type);
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params,
					httpHeaders);
			RestTemplate restTemplate = new RestTemplate();
			response = restTemplate.postForEntity(uri, request, LoginResponse.class);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return response.getBody();
	}
}
