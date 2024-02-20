package com.customer.info.corptech.salesforce.scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.customer.info.corptech.dao.CustomerRepository;
import com.customer.info.corptech.entities.Customer;
import com.customer.info.corptech.entities.LoginResponse;
import com.customer.info.corptech.service.LoginOperation;
import com.customer.info.corptech.service.SalesForceDataService;

@Component
public class SalesForceScheduler {
	private static final Logger logger = LoggerFactory.getLogger(SalesForceScheduler.class);
	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
	@Autowired
	SalesForceDataService salesForceDataService;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	LoginOperation loginOperation;
	@Scheduled(cron = "@Daily")
	public void RunSalesForceIntegration() throws Exception {
		List<Customer> customers = customerRepository.getCustomers();
		if(customers != null && customers.size() > 0) {
			LoginResponse loginResponse =  loginOperation.login();
			salesForceDataService.updateMultipleCustomers(loginResponse, customers);
		}
		logger.info("Fixed task scheduled for every Day :: Execution Time"+dateFormat.format(LocalDateTime.now()));
	}

}
