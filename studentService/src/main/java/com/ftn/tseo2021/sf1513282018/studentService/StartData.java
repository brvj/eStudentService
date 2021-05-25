package com.ftn.tseo2021.sf1513282018.studentService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.AuthorityService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserAuthorityService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;

@Component
public class StartData {
	
	@Autowired
	private InstitutionService iService;
	
	@Autowired
	private AuthorityService aService;
	
	@Autowired
	private UserService uService;
	
	@Autowired
	private UserAuthorityService uaService;
	
	@PostConstruct
	private void init() {
		
//		int iId = iService.create(new DefaultInstitutionDTO(null, "Fakultet tehničkih nauka", "Trg Dositeja Obradovića 6, Novi Sad", "+381 21 450 810"));
//
//		int aId = aService.create(new DefaultAuthorityDTO(null, "ADMIN"));
//
//		int uId = uService.create(new DefaultUserDTO(null, "admin", "admin", "Admin", "Admin", "admin.admin@student-service.com", "064-351-35-75", new DefaultInstitutionDTO(iId, null, null, null), null));
//
//		uaService.create(new DefaultUserAuthorityDTO(null, new DefaultUserDTO(uId, null, null, null, null, null, null, null, null), new DefaultAuthorityDTO(aId, null)));
		
	}

}
