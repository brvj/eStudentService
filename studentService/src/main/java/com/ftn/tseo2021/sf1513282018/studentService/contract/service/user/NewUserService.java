package com.ftn.tseo2021.sf1513282018.studentService.contract.service.user;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserCreate;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserUpdate;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.views.InstitutionUser;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.views.UserView;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.NewBaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.UserUserAuthorityDTO;

public interface NewUserService extends NewBaseService<UserView, Integer, UserCreate, UserUpdate>, UserDetailsService {
	
	UserView getByUsername(String username);
	
	List<UserUserAuthorityDTO> getUserUserAuthorities(int userId, Pageable pageable);
	
	List<InstitutionUser> filterUsers(int institutionId, Pageable pageable, DefaultUserDTO filterOptions);
	
}
