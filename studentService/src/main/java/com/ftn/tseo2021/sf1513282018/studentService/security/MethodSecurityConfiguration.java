package com.ftn.tseo2021.sf1513282018.studentService.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(
		prePostEnabled = true
		)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

}
