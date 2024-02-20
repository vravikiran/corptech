package com.customer.info.corptech.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.customer.info.corptech.entities.Attributes;
import com.customer.info.corptech.entities.Customer;
import com.customer.info.corptech.entities.CustomerSFEntity;
import com.customer.info.corptech.entities.LoginResponse;
import com.customer.info.corptech.entities.Records;

@Service
public class SalesForceDataService {
	public ResponseEntity<?> updateMultipleCustomers(LoginResponse loginResponse, List<Customer> customers)
			throws Exception {
		String baseUrl = loginResponse.getInstance_url() + "/services/data/v59.0/composite/tree/Customer__C";
		List<CustomerSFEntity> customerSFList = new ArrayList<>();
		CustomerSFEntity customerSFEntity = null;
		Attributes attributes = null;
		for (Customer customer : customers) {
			attributes = new Attributes("ref" + customer.getCustomerId());
			customerSFEntity = new CustomerSFEntity(attributes, customer.getCustomerId(), customer.getName(),
					customer.getEmail(), customer.getPhoneNo());
			customerSFList.add(customerSFEntity);
		}
		Records records = new Records();
		records.setRecords(customerSFList);
		HttpEntity<Records> httpEntity = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", loginResponse.getAccess_token()));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpEntity = new HttpEntity<>(records, httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> responseEntity = null;
		try {
			URI uri = new URI(baseUrl);
			responseEntity = restTemplate.postForEntity(uri, httpEntity, Object.class);
		} catch (URISyntaxException uriSyntaxException) {
			System.out.print(uriSyntaxException.getMessage());
		} catch (Exception exception) {
			System.out.println("Exception occurred while updatring records from DB to Salesforce due to below error::"
					+ exception.getMessage());
		}
		return responseEntity;
	}
}
