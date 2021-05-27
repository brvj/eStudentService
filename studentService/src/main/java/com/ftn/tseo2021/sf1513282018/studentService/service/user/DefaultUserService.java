package com.ftn.tseo2021.sf1513282018.studentService.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.InstitutionUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.UserUserAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.UserAuthority;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;

@Primary
@Service
public class DefaultUserService implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private DtoConverter<User, UserDTO, DefaultUserDTO> userConverter;
	
	@Autowired
	private UserAuthorityService userAuthorityService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private PersonalizedAuthorizator authorizator;
	
	private CustomPrincipal getPrincipal() { return authorizator.getPrincipal(); }
	
	@AuthorizeAny
	@Override
	public DefaultUserDTO getOne(Integer id) {
		if (!getPrincipal().isAdmin() && 
				(getPrincipal().isTeacher() || getPrincipal().isStudent()))
			authorizator.assertPrincipalIdIs(id, PersonalizedAccessDeniedException.class);
		
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(user.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		
		user.setPassword(null);
		return userConverter.convertToDTO(user);
	}

	@AuthorizeAdmin
	@Override
	public Integer create(DefaultUserDTO dto) {
		authorizator.assertPrincipalIsFromInstitution(dto.getInstitution().getId(), EntityValidationException.class);
		
		User user = userConverter.convertToJPA(dto);
		
		user = userRepo.save(user);

		return user.getId();
	}

	@AuthorizeAny
	@Override
	public void update(Integer id, DefaultUserDTO dto) {
		if (!getPrincipal().isAdmin() && 
				(getPrincipal().isTeacher() || getPrincipal().isStudent()))
			authorizator.assertPrincipalIdIs(id, PersonalizedAccessDeniedException.class);
		
		User u = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(u.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		
		if (dto.getInstitution().getId() != u.getInstitution().getId()) throw new EntityValidationException();

		User uNew = userConverter.convertToJPA(dto);
		
		u.setUsername(uNew.getUsername());
//		u.setPassword(uNew.getPassword());
		u.setFirstName(uNew.getFirstName());
		u.setLastName(uNew.getLastName());
		u.setEmail(uNew.getEmail());
		u.setPhoneNumber(uNew.getPhoneNumber());
		userRepo.save(u);
	}

	@AuthorizeAdmin
	@Override
	public void delete(Integer id) {
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		authorizator.assertPrincipalIsFromInstitution(user.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		
		userRepo.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@AuthorizeAdmin
	@Override
	public Page<InstitutionUserDTO> filterUsers(int institutionId, Pageable pageable, DefaultUserDTO filterOptions) {
		authorizator.assertPrincipalIsFromInstitution(institutionId, PersonalizedAccessDeniedException.class);

		Page<User> page;

		if (filterOptions == null) {
			page = userRepo.findByInstitution_Id(institutionId, pageable);
		}
		else {
			page = userRepo.filterUsers(institutionId, filterOptions.getUsername(), filterOptions.getFirstName(),
					filterOptions.getLastName(), filterOptions.getEmail(), pageable);
		}
		return page.map(new Function<User, InstitutionUserDTO>() {
			@Override
			public InstitutionUserDTO apply(User user) {
				return userConverter.convertToDTO(user, InstitutionUserDTO.class);
			}
		});
	}
	
	@AuthorizeAdmin
	@Override
	public Page<InstitutionUserDTO> getAdminsForInstitution(int institutionId, Pageable pageable) {
		authorizator.assertPrincipalIsFromInstitution(institutionId, PersonalizedAccessDeniedException.class);

		Page<User> page = userRepo.findByInstitution_IdAndUserAuthorities_Authority_Name(institutionId, "ADMIN", pageable);
		
		return page.map(new Function<User, InstitutionUserDTO>() {
			@Override
			public InstitutionUserDTO apply(User t) {
				return userConverter.convertToDTO(t, InstitutionUserDTO.class);
			}
		});
	}
	
	@AuthorizeAdmin
	@Override
	public Page<InstitutionUserDTO> filterAdminsForInstitution(int institutionId, Pageable pageable) {
		authorizator.assertPrincipalIsFromInstitution(institutionId, PersonalizedAccessDeniedException.class);
		
		Page<User> page = userRepo.filterAdmins(institutionId, null, null, null, null, pageable);
		
		return page.map(new Function<User, InstitutionUserDTO>() {
			@Override
			public InstitutionUserDTO apply(User t) {
				return userConverter.convertToDTO(t, InstitutionUserDTO.class);
			}
		});
	}
	
//	---------------------------------------------------------------------------------------------------------------------------
//	No need to explicitly secure methods below since they completely depend on secured methods of another services
	
	@Override
	public List<UserUserAuthorityDTO> getUserUserAuthorities(int userId, Pageable pageable) {
		if (!userRepo.existsById(userId)) throw new ResourceNotFoundException();
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
		
		return new CustomPrincipal(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getInstitution().getId(), ownerId, grantedAuthorities);
	}

}
