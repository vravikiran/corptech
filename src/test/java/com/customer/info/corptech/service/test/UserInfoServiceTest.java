package com.customer.info.corptech.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.customer.info.corptech.dao.UserInfoRepository;
import com.customer.info.corptech.entities.UserInfo;
import com.customer.info.corptech.service.UserInfoService;

@ExtendWith(MockitoExtension.class)
public class UserInfoServiceTest {
	@Mock
	UserInfoRepository userInfoRepository;
	@Mock
	PasswordEncoder passwordEncoder;
	@InjectMocks
	UserInfoService userInfoService;

	@Test
	public void testAddUserInfo_WithValidData() {
		UserInfo userInfo = getUserInfo();
		when(userInfoRepository.save(any(UserInfo.class))).thenReturn(userInfo);
		UserInfo resultUserInfo = userInfoService.addUserName(userInfo);
		assertEquals(userInfo, resultUserInfo);
		assertThat(resultUserInfo).isNotNull();
	}

	@Test
	public void testAddUserInfo_WithInValidData() {
		UserInfo userInfo = getUserInfo();
		when(userInfoRepository.save(any(UserInfo.class))).thenThrow(DataIntegrityViolationException.class);
		assertThrows(DataIntegrityViolationException.class,  ()->userInfoService.addUserName(userInfo));
	
	}
	
	@Test
	public void testloadUserByUsername_WithValidUserName() {
		UserInfo userInfo = getUserInfo();
		when(userInfoRepository.findByName(anyString())).thenReturn(Optional.of(userInfo));
		UserDetails result = userInfoService.loadUserByUsername("ravi");
		assertTrue(userInfo.getName().equals(result.getUsername()));
	}
	
	@Test
	public void testloadUserByUsername_WithInValidUserName() {
		when(userInfoRepository.findByName(anyString())).thenThrow(UsernameNotFoundException.class);
		assertThrows(UsernameNotFoundException.class, ()->userInfoService.loadUserByUsername("ravi"));
	}
	

	private UserInfo getUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail("vvrk@gmail.com");
		userInfo.setId(12);
		userInfo.setName("ravi");
		userInfo.setPassword("password");
		userInfo.setRoles("ADMIN");
		return userInfo;
	}
}
