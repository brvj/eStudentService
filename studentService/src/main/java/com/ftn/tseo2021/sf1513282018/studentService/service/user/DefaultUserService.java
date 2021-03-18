package com.ftn.tseo2021.sf1513282018.studentService.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.UserRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;

@Service
public class DefaultUserService implements UserService {
	
	@Autowired
	UserRepository userRepo;

	@Override
	public DefaultUserDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(DefaultUserDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, DefaultUserDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
