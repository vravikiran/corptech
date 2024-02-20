package com.customer.info.corptech.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.customer.info.corptech.entities.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	@Query(value = "select * from customer where creation_time >= CAST(NOW() - INTERVAL 1 DAY AS DATE) and creation_time <= cast(now()+INTERVAL 1 DAY as DATE)", nativeQuery = true)
	public List<Customer> getCustomers();
}
