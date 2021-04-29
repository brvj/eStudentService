package com.ftn.tseo2021.sf1513282018.studentService.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.LoginDTO;
import com.ftn.tseo2021.sf1513282018.studentService.security.CurrentPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.TokenUtils;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@GetMapping
	public ModelAndView getInstituionUsers(@CurrentPrincipal CustomPrincipal principal) {
		return new ModelAndView(String.format("forward:/api/institutions/%d/users", principal.getInstitutionId()));
	}
	
	@PostMapping(value = "/login", consumes = "application/json")
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
			authManager.authenticate(token);
			UserDetails details = userService.loadUserByUsername(loginDTO.getUsername());
			String jwt = tokenUtils.generateToken(details);
			return new ResponseEntity<>(jwt, HttpStatus.OK);
		} catch (AuthenticationException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
