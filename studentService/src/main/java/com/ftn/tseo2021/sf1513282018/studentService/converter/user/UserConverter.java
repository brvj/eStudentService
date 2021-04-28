package com.ftn.tseo2021.sf1513282018.studentService.converter.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.AuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.InstitutionUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.Authority;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.UserAuthority;

@Component
public class UserConverter implements DtoConverter<User, UserDTO, DefaultUserDTO> {
	
	@Autowired
	private InstitutionRepository institutionRepo;
	
	@Autowired
	private DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> institutionConverter;
	
	@Autowired
	private DtoConverter<Authority, AuthorityDTO, DefaultAuthorityDTO> authorityConverter;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User convertToJPA(UserDTO source) throws IllegalArgumentException {
		if (source instanceof DefaultUserDTO) return convertToJPA((DefaultUserDTO) source);
		else throw new IllegalArgumentException(String.format(
					"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<User> convertToJPA(List<? extends UserDTO> sources) throws IllegalArgumentException {
		List<User> result = new ArrayList<User>();
		
		if (sources.get(0) instanceof DefaultUserDTO) {
			for (UserDTO dto : sources) result.add(convertToJPA((DefaultUserDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends UserDTO> T convertToDTO(User source, Class<? extends UserDTO> returnType) throws IllegalArgumentException {
		if (returnType == DefaultUserDTO.class) return (T) convertToDefaultUserDTO(source);
		else if (returnType == InstitutionUserDTO.class) return (T) convertToInstitutionUserDTO(source);
		else throw new IllegalArgumentException(String.format(
					"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends UserDTO> convertToDTO(List<User> sources, Class<? extends UserDTO> returnType) throws IllegalArgumentException {
		if (returnType == DefaultUserDTO.class) {
			List<DefaultUserDTO> result = new ArrayList<>();
			for (User jpa : sources) result.add(convertToDefaultUserDTO(jpa));
			return result;
		}
		else if (returnType == InstitutionUserDTO.class) {
			List<InstitutionUserDTO> result = new ArrayList<>();
			for (User jpa : sources) result.add(convertToInstitutionUserDTO(jpa));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
					"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultUserDTO convertToDTO(User source) {
		return convertToDefaultUserDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultUserDTO> convertToDTO(List<User> sources) {
		return (List<DefaultUserDTO>) convertToDTO(sources, DefaultUserDTO.class);
	}
	
	private DefaultUserDTO convertToDefaultUserDTO(User source) {
		if (source == null) return null;
		
		List<DefaultAuthorityDTO> authorities = new ArrayList<>();
		for (UserAuthority ua : source.getUserAuthorities()) {
			authorities.add(authorityConverter.convertToDTO(ua.getAuthority()));
		}
		
		DefaultUserDTO dto = new DefaultUserDTO(source.getId(), source.getUsername(), source.getPassword(), 
				source.getFirstName(), source.getLastName(), source.getEmail(), source.getPhoneNumber(), 
				institutionConverter.convertToDTO(source.getInstitution()), authorities);
		
		return dto;
	}
	
	private InstitutionUserDTO convertToInstitutionUserDTO(User source) {
		if (source == null) return null;
		
		List<DefaultAuthorityDTO> authorities = new ArrayList<>();
		for (UserAuthority ua : source.getUserAuthorities()) {
			authorities.add(authorityConverter.convertToDTO(ua.getAuthority()));
		}
		
		InstitutionUserDTO dto = new InstitutionUserDTO(source.getId(), source.getUsername(), source.getFirstName(), 
				source.getLastName(), source.getEmail(), source.getPhoneNumber(), authorities);
		
		return dto;
	}
	
	private User convertToJPA(DefaultUserDTO source) throws IllegalArgumentException {
		if (source == null) return null;
		
		if (source.getInstitution() == null || !institutionRepo.existsById(source.getInstitution().getId()))
			throw new IllegalArgumentException();
		
		User user = new User();
//		user.setId(source.getId());
		user.setUsername(source.getUsername());
		user.setPassword(passwordEncoder.encode(source.getPassword()));
		user.setFirstName(source.getFirstName());
		user.setLastName(source.getLastName());
		user.setEmail(source.getEmail());
		user.setPhoneNumber(source.getPhoneNumber());
		user.setInstitution(institutionRepo.getOne(source.getInstitution().getId()));
		
		return user;
	}

}
