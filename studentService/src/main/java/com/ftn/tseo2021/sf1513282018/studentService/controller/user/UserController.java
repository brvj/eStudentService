package com.ftn.tseo2021.sf1513282018.studentService.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserCreate;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserUpdate;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.views.UserView;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.NewUserService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.LoginDTO;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.TokenUtils;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.CurrentPrincipal;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
	
	@Autowired
	private NewUserService userService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<UserView> getOne(@PathVariable("id") int id) {
		UserView user = userService.getOne(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createUser(@Validated @RequestBody UserCreate userDTO) {
		int userId = userService.create(userDTO);
		return new ResponseEntity<>(userId, HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateUser(@PathVariable("id") int id, @Validated @RequestBody UserUpdate userDTO) {
		userService.update(id, userDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping
	public ModelAndView getInstituionUsers(@CurrentPrincipal CustomPrincipal principal) {
		return new ModelAndView(String.format("forward:/api/institutions/%d/users", principal.getInstitutionId()));
	}
	
	@GetMapping(value = "/admins", produces = "application/json")
	public ModelAndView getInstitutionAdmins(@CurrentPrincipal CustomPrincipal principal) {
		return new ModelAndView(String.format("forward:/api/institutions/%d/admins", principal.getInstitutionId()));
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
