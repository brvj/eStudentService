package com.ftn.tseo2021.sf1513282018.studentService.contract.service.user;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserAuthorityDTO;

public interface AuthorityService extends BaseService<DefaultAuthorityDTO, Integer> {
	
	List<DefaultUserAuthorityDTO> getAuthorityUserAuthorities(int authorityId, Pageable pageable) throws EntityNotFoundException;

}
