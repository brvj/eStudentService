package com.ftn.tseo2021.sf1513282018.studentService.contract.service.user;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.InstitutionUserDTO;

public interface UserService extends BaseService<DefaultUserDTO, Integer> {
	
	DefaultUserDTO getByUsername(String username);
	
	List<InstitutionUserDTO> getByInstitutionId(int institutionId, Pageable pageable);
	
	List<DefaultUserAuthorityDTO> getUserUserAuthorities(int userId, Pageable pageable) throws EntityNotFoundException;
	
	List<DefaultUserDTO> filterUsers(DefaultUserDTO filterOptions, Pageable pageable);
	
}
