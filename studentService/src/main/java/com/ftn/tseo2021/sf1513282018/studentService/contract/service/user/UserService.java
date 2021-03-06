package com.ftn.tseo2021.sf1513282018.studentService.contract.service.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserFilterOptions;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.InstitutionUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.UserUserAuthorityDTO;

public interface UserService extends BaseService<DefaultUserDTO, Integer>, UserDetailsService {
	
	List<UserUserAuthorityDTO> getUserUserAuthorities(int userId, Pageable pageable);
	
	Page<InstitutionUserDTO> filterUsers(int institutionId, Pageable pageable, DefaultUserDTO filterOptions);

	Page<InstitutionUserDTO> getAdminsForInstitution(int institutionId, Pageable pageable);

	Page<InstitutionUserDTO> filterAdminsForInstitution(int institutionId, Pageable pageable, UserFilterOptions filterOptions);
	
}
