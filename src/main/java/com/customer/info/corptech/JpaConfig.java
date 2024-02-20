package com.customer.info.corptech;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.customer.info.corptech.dao"})
public class JpaConfig {

}
