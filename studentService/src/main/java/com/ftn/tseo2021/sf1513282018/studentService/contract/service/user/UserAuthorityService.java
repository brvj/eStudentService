package com.ftn.tseo2021.sf1513282018.studentService.contract.service.user;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.UserUserAuthorityDTO;

public interface UserAuthorityService extends BaseService<DefaultUserAuthorityDTO, Integer> {
	
	List<UserUserAuthorityDTO> getByUserId(int userId, Pageable pageable);
	
}
