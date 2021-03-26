package com.ftn.tseo2021.sf1513282018.studentService.contract.service.user;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserAuthorityDTO;

public interface UserAuthorityService extends BaseService<DefaultUserAuthorityDTO, Integer> {
	
	List<DefaultUserAuthorityDTO> getByUserId(int userId, Pageable pageable);
	
	List<DefaultUserAuthorityDTO> getByAuthorityId(int authorityId, Pageable pageable);

}
