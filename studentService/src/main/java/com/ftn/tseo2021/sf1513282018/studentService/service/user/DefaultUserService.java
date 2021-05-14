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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.UserRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserAuthorityService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.InstitutionUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.UserUserAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.UserAuthority;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.PrincipalHolder;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAdmin;

@Service
public class DefaultUserService implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	DtoConverter<User, UserDTO, DefaultUserDTO> userConverter;
	
	@Autowired
	UserAuthorityService userAuthorityService;
	
	@Autowired
	TeacherService teacherService;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	private PrincipalHolder principalHolder;
	
	private void authorize(Integer institutionId) throws PersonalizedAccessDeniedException {
		if (principalHolder.getCurrentPrincipal().getInstitutionId() != institutionId) 
			throw new PersonalizedAccessDeniedException();
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
		
		User u = userRepo.findById(id).get();
		u.setUsername(uNew.getUsername());
//		u.setPassword(uNew.getPassword()); ?
		u.setFirstName(uNew.getFirstName());
		u.setLastName(uNew.getLastName());
		u.setEmail(uNew.getEmail());
		u.setPhoneNumber(uNew.getPhoneNumber());
		userRepo.save(u);
		
	}

	@Override
	public void delete(Integer id) {
		if (!userRepo.existsById(id)) {};
		userRepo.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAdmin
	@Override
	public List<InstitutionUserDTO> filterUsers(int institutionId, Pageable pageable, DefaultUserDTO filterOptions) throws PersonalizedAccessDeniedException {
		authorize(institutionId);
		
		if (filterOptions == null) {
			Page<User> page = userRepo.findByInstitution_Id(institutionId, pageable);
			return (List<InstitutionUserDTO>) userConverter.convertToDTO(page.getContent(), InstitutionUserDTO.class);
		}
		else {
			Page<User> page = userRepo.filterUsers(institutionId, filterOptions.getUsername(), filterOptions.getFirstName(), 
					filterOptions.getLastName(), filterOptions.getEmail() , pageable);
			return (List<InstitutionUserDTO>) userConverter.convertToDTO(page.getContent(), InstitutionUserDTO.class);
		}
	}
	
	@Override
	public List<UserUserAuthorityDTO> getUserUserAuthorities(int userId, Pageable pageable) throws EntityNotFoundException {
		if (!userRepo.existsById(userId)) throw new EntityNotFoundException();
		return userAuthorityService.getByUserId(userId, pageable);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> u = userRepo.findByUsername(username);
		if (u.isEmpty()) throw new UsernameNotFoundException(String.format("User with username: %s not found", username));
		
		User user = u.get();
		boolean isTeacher = false;
		boolean isStudent = false;
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (UserAuthority ua : user.getUserAuthorities()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(ua.getAuthority().getName()));
			
			if (ua.getAuthority().getName().equals("TEACHER")) isTeacher = true;
			else if (ua.getAuthority().getName().equals("STUDENT")) isStudent = true;
		}
		
		Integer ownerId = null;
		if (isTeacher) {
			ownerId = teacherService.getByUserId(user.getId()).getId();
		}
		else if (isStudent) {
			ownerId = studentService.getByUserId(user.getId()).getId();
		}
		
		return new CustomPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getInstitution().getId(), ownerId, grantedAuthorities);
	}

}
