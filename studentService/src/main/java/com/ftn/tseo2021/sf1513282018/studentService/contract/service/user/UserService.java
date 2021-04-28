package com.ftn.tseo2021.sf1513282018.studentService.contract.service.user;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.InstitutionUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.UserUserAuthorityDTO;

public interface UserService extends BaseService<DefaultUserDTO, Integer>, UserDetailsService {
	
	DefaultUserDTO getByUsername(String username);
	
	List<UserUserAuthorityDTO> getUserUserAuthorities(int userId, Pageable pageable) throws EntityNotFoundException;
	
	List<InstitutionUserDTO> filterUsers(int institutionId, Pageable pageable, DefaultUserDTO filterOptions);
	
}
