package com.ftn.tseo2021.sf1513282018.studentService.service.user;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.UserRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserService;
import com.ftn.tseo2021.sf1513282018.studentService.model.common.UserType;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;

@Service
public class DefaultUserService implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	DtoConverter<User, UserDTO, DefaultUserDTO> userConverter;

	@Override
	public DefaultUserDTO getOne(Integer id) {
		Optional<User> u = userRepo.findById(id);
		return userConverter.convertToDTO(u.orElse(null));
	}

	@Override
	public Integer create(DefaultUserDTO dto) {
		User user = userConverter.convertToJPA(dto);
		
		user = userRepo.save(user);
		
		return user.getId();
	}

	@Override
	public void update(Integer id, DefaultUserDTO dto) {
		if (!userRepo.existsById(id)) throw new EntityNotFoundException();
		
		dto.setId(id);
		User user = userConverter.convertToJPA(dto);
		
		userRepo.save(user);
		
	}

	@Override
	public boolean delete(Integer id) {
		if (!userRepo.existsById(id)) return false;
		userRepo.deleteById(id);
		return true;
	}

	@Override
	public DefaultUserDTO getByUsername(String username) {
		Optional<User> u = userRepo.findByUsername(username);
		return userConverter.convertToDTO(u.orElse(null));
	}

	@Override
	public List<DefaultUserDTO> getByInstitutionId(int institutionId, Pageable pageable) {
		Page<User> page = userRepo.filterUsers(institutionId, null, null, null, null, pageable);
		return userConverter.convertToDTO(page.getContent());
	}

	@Override
	public List<DefaultUserDTO> filterUsers(DefaultUserDTO filterOptions, Pageable pageable) {
		Page<User> page = userRepo.filterUsers(0, filterOptions.getUsername(), filterOptions.getFirstName(), 
				filterOptions.getLastName(), UserType.valueOf(filterOptions.getUserType()), pageable);
		return userConverter.convertToDTO(page.getContent());
	}

}
