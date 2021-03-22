package com.ftn.tseo2021.sf1513282018.studentService.contract.service.user;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;

public interface UserService extends BaseService<DefaultUserDTO, Integer> {
	
	DefaultUserDTO getByUsername(String username);
	
	List<DefaultUserDTO> getByInstitutionId(int institutionId, Pageable pageable);
	
	List<DefaultUserDTO> filterUsers(DefaultUserDTO filterOptions, Pageable pageable);

}
