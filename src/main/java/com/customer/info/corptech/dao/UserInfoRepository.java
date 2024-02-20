package com.customer.info.corptech.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.customer.info.corptech.entities.UserInfo;
@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo,Integer> {
	public Optional<UserInfo> findByName(String name); 
}
