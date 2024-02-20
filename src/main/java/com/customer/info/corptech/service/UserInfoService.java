package com.customer.info.corptech.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.customer.info.corptech.dao.UserInfoRepository;
import com.customer.info.corptech.entities.UserInfo;
import com.customer.info.corptech.entities.UserInfoDetails;

@Service
public class UserInfoService implements UserDetailsService {
	@Autowired
	UserInfoRepository userInfoRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userInfo = userInfoRepository.findByName(username);
		return userInfo.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}

	public UserInfo addUserName(UserInfo userInfo) {
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		return userInfoRepository.save(userInfo);
	}
}
