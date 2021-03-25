package com.ftn.tseo2021.sf1513282018.studentService.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
	
	@Autowired
	UserService userService;

}
