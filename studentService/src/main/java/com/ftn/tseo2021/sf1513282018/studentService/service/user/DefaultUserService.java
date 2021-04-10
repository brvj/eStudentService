package com.ftn.tseo2021.sf1513282018.studentService.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.UserRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserAuthorityService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.InstitutionUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.UserAuthority;

@Service
public class DefaultUserService implements UserService, UserDetailsService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	DtoConverter<User, UserDTO, DefaultUserDTO> userConverter;
	
	@Autowired
	UserAuthorityService userAuthorityService;
	
	@Override
	public boolean existsById(Integer id) {
		return userRepo.existsById(id);
	}

	@Override
	public DefaultUserDTO getOne(Integer id) {
		Optional<User> u = userRepo.findById(id);
		return userConverter.convertToDTO(u.orElse(null));
	}

	@Override
	public Integer create(DefaultUserDTO dto) throws IllegalArgumentException {
		User user = userConverter.convertToJPA(dto);
		
		user = userRepo.save(user);
		
		return user.getId();
	}

	@Override
	public void update(Integer id, DefaultUserDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if (!userRepo.existsById(id)) throw new EntityNotFoundException();

		User uNew = userConverter.convertToJPA(dto);
		
//		REAL PUT
//		uNew.setId(id);
//		userRepo.save(uNew);
		
//		SIMULATE PATCH
		User u = userRepo.getOne(id);
		u.setUsername(uNew.getUsername());
//		u.setPassword(uNew.getPassword()); ?
		u.setFirstName(uNew.getFirstName());
		u.setLastName(uNew.getLastName());
		u.setEmail(uNew.getEmail());
		u.setPhoneNumber(uNew.getPhoneNumber());
		u.setInstitution(uNew.getInstitution());
		userRepo.save(u);
		
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

	@SuppressWarnings("unchecked")
	@Override
	public List<InstitutionUserDTO> getByInstitutionId(int institutionId, Pageable pageable) {
		Page<User> page = userRepo.filterUsers(institutionId, null, null, null, null, pageable);
		return (List<InstitutionUserDTO>) userConverter.convertToDTO(page.getContent(), InstitutionUserDTO.class);
	}

	@Override
	public List<DefaultUserDTO> filterUsers(DefaultUserDTO filterOptions, Pageable pageable) {
		Page<User> page = userRepo.filterUsers(0, filterOptions.getUsername(), filterOptions.getFirstName(), 
				filterOptions.getLastName(), filterOptions.getEmail() , pageable);
		return userConverter.convertToDTO(page.getContent());
	}
	
	@Override
	public List<DefaultUserAuthorityDTO> getUserUserAuthorities(int userId, Pageable pageable) throws EntityNotFoundException {
		if (!userRepo.existsById(userId)) throw new EntityNotFoundException();
		return userAuthorityService.getByUserId(userId, pageable);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> u = userRepo.findByUsername(username);
		if (u.isEmpty()) throw new UsernameNotFoundException(String.format("User with username: %s not found", username));
		
		User user = u.get();
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (UserAuthority ua : user.getUserAuthorities())
			grantedAuthorities.add(new SimpleGrantedAuthority(ua.getAuthority().getName()));
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}

}
