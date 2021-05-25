package com.ftn.tseo2021.sf1513282018.studentService.converter.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.AuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserCreate;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserUpdate;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.views.InstitutionUser;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.views.UserView;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.AuthorityRepository;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.Authority;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.UserAuthority;

@Component
public class NewUserConverter implements DtoConverter<User, UserDTO, UserView> {
	
	private static final String DEFAULT_PASSWORD = "fakultet";
	
	@Autowired
	private InstitutionRepository institutionRepo;
	
	@Autowired
	private AuthorityRepository authorityRepo;
	
	@Autowired
	private DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> institutionConverter;
	
	@Autowired
	private DtoConverter<Authority, AuthorityDTO, DefaultAuthorityDTO> authorityConverter;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User convertToJPA(UserDTO source) {
		if (source instanceof UserCreate) return convertToJPA((UserCreate) source);
		else if (source instanceof UserUpdate) return convertToJPA((UserUpdate) source);
		else throw new IllegalArgumentException(String.format(
					"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<User> convertToJPA(List<? extends UserDTO> sources) {
		List<User> result = new ArrayList<User>();
		
		if (sources.get(0) instanceof UserCreate) {
			for (UserDTO dto : sources) result.add(convertToJPA((UserCreate) dto));
			return result;
		}
		else if (sources.get(0) instanceof UserUpdate) {
			for (UserDTO dto : sources) result.add(convertToJPA((UserUpdate) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends UserDTO> T convertToDTO(User source, Class<? extends UserDTO> returnType) {
		if (returnType == UserView.class) return (T) convertToUserView(source);
		else if (returnType == InstitutionUser.class) return (T) convertToInstitutionUser(source);
		else throw new IllegalArgumentException(String.format(
					"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends UserDTO> convertToDTO(List<User> sources, Class<? extends UserDTO> returnType) {
		if (returnType == UserView.class) {
			List<UserView> result = new ArrayList<>();
			for (User jpa : sources) result.add(convertToUserView(jpa));
			return result;
		}
		else if (returnType == InstitutionUser.class) {
			List<InstitutionUser> result = new ArrayList<>();
			for (User jpa : sources) result.add(convertToInstitutionUser(jpa));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
					"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public UserView convertToDTO(User source) {
		return convertToUserView(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserView> convertToDTO(List<User> sources) {
		return (List<UserView>) convertToDTO(sources, UserView.class);
	}
	
	private com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.user.UserView convertToUserViewDTO(User source) {
		if (source == null) return null;
		
		List<DefaultAuthorityDTO> authorities = new ArrayList<>();
		for (UserAuthority ua : source.getUserAuthorities()) {
			authorities.add(authorityConverter.convertToDTO(ua.getAuthority()));
		}
		
		com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.user.UserView dto
				= new com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.user.UserView(source.getId(), source.getUsername(), 
					source.getFirstName(), source.getLastName(), source.getEmail(), source.getPhoneNumber(), 
					institutionConverter.convertToDTO(source.getInstitution()), authorities);
		
		return dto;
	}
	
	private UserView convertToUserView(User source) {
		return convertToUserViewDTO(source);
	}
	
	private InstitutionUser convertToInstitutionUser(User source) {
		if (source == null) return null;
		
		source.setInstitution(null);
		
		return convertToUserViewDTO(source);
	}
	
	private User convertToJPA(UserCreate source) {
		if (source == null || source.getInstitutionId() == null || !institutionRepo.existsById(source.getInstitutionId()))
			throw new EntityValidationException();
		
		for (DefaultAuthorityDTO authority : source.getAuthorities()) 
			if (!authorityRepo.existsById(authority.getId()))
				throw new EntityValidationException();
		try {
			User user = new User();
			user.setUsername(source.getUsername());
			user.setPassword(passwordEncoder.encode(source.getPassword() == null ? DEFAULT_PASSWORD : source.getPassword()));
			user.setFirstName(source.getFirstName());
			user.setLastName(source.getLastName());
			user.setEmail(source.getEmail());
			user.setPhoneNumber(source.getPhoneNumber());
			user.setInstitution(institutionRepo.getOne(source.getInstitutionId()));
			
			Set<UserAuthority> authorities = new HashSet<>();
			for (DefaultAuthorityDTO dto : source.getAuthorities()) {
				UserAuthority ua = new UserAuthority();
				ua.setUser(user);
				ua.setAuthority(authorityRepo.getOne(dto.getId()));
				authorities.add(ua);
			}
			user.setUserAuthorities(authorities);
			
			return user;
		}
		catch (ConstraintViolationException | EntityNotFoundException ex) {
			throw new EntityValidationException(ex);
		}
	}
	
	private User convertToJPA(UserUpdate source) {
		if (source == null)
			throw new EntityValidationException();
		
		try {
			User user = new User();
			user.setUsername(source.getUsername());
			user.setFirstName(source.getFirstName());
			user.setLastName(source.getLastName());
			user.setEmail(source.getEmail());
			user.setPhoneNumber(source.getPhoneNumber());
			
			return user;
		}
		catch (ConstraintViolationException | EntityNotFoundException ex) {
			throw new EntityValidationException(ex);
		}
	}

}
