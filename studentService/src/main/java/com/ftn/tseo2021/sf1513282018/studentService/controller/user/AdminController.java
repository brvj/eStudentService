package com.ftn.tseo2021.sf1513282018.studentService.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserCreate;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserUpdate;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.NewUserService;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.CurrentPrincipal;

@RestController
@RequestMapping(value = "api/admins")
public class AdminController {
	
	@Autowired
	private NewUserService userService;
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ModelAndView getOne(@PathVariable("id") int id) {
		return new ModelAndView(String.format("forward:/api/users/%d", id));
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createAdmin(@Validated @RequestBody UserCreate adminDTO) {
		int adminId = userService.create(adminDTO);
		return new ResponseEntity<>(adminId, HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateAdmin(@PathVariable("id") int id, @Validated @RequestBody UserUpdate adminDTO) {
		userService.update(id, adminDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteAdmin(@PathVariable("id") int id) {
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(produces = "application/json")
	public ModelAndView getInstitutionAdmins(@CurrentPrincipal CustomPrincipal principal) {
		return new ModelAndView(String.format("forward:/api/institutions/%d/admins", principal.getInstitutionId()));
	}

}
