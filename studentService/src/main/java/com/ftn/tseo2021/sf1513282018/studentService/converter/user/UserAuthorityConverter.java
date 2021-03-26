package com.ftn.tseo2021.sf1513282018.studentService.converter.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.AuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.AuthorityRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.UserRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.Authority;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.UserAuthority;

public class UserAuthorityConverter implements DtoConverter<UserAuthority, UserAuthorityDTO, DefaultUserAuthorityDTO> {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	AuthorityRepository authorityRepo;
	
	@Autowired
	DtoConverter<User, UserDTO, DefaultUserDTO> userConverter;
	
	@Autowired
	DtoConverter<Authority, AuthorityDTO, DefaultAuthorityDTO> authorityConverter;
	
	@Override
	public UserAuthority convertToJPA(UserAuthorityDTO source) throws IllegalArgumentException {
		if (source instanceof DefaultUserAuthorityDTO) return convertToJPA((DefaultUserAuthorityDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<UserAuthority> convertToJPA(List<? extends UserAuthorityDTO> sources) throws IllegalArgumentException {
		List<UserAuthority> result = new ArrayList<>();
		
		if (sources.get(0) instanceof DefaultUserAuthorityDTO) {
			for (UserAuthorityDTO dto : sources) result.add(convertToJPA((DefaultUserAuthorityDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends UserAuthorityDTO> T convertToDTO(UserAuthority source,
			Class<? extends UserAuthorityDTO> returnType) {
		if (returnType == DefaultUserAuthorityDTO.class) return (T) convertToDefaultUserAuthorityDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends UserAuthorityDTO> convertToDTO(List<UserAuthority> sources,
			Class<? extends UserAuthorityDTO> returnType) {
		if (returnType == DefaultUserAuthorityDTO.class) {
			List<DefaultUserAuthorityDTO> result = new ArrayList<>();
			for (UserAuthority jpa : sources) result.add(convertToDefaultUserAuthorityDTO(jpa));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultUserAuthorityDTO convertToDTO(UserAuthority source) {
		return convertToDefaultUserAuthorityDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultUserAuthorityDTO> convertToDTO(List<UserAuthority> sources) {
		return (List<DefaultUserAuthorityDTO>) convertToDTO(sources, DefaultUserAuthorityDTO.class);
	}
	
	private DefaultUserAuthorityDTO convertToDefaultUserAuthorityDTO(UserAuthority source) {
		if (source == null) return null;
		
		DefaultUserAuthorityDTO dto = new DefaultUserAuthorityDTO(source.getId(), 
				userConverter.convertToDTO(source.getUser()), 
				authorityConverter.convertToDTO(source.getAuthority()));
		
		return dto;
	}
	
	private UserAuthority convertToJPA(DefaultUserAuthorityDTO source) throws IllegalArgumentException {
		if (source == null) return null;
		
		if (source.getAuthority() == null || source.getUser() == null ||
				!authorityRepo.existsById(source.getAuthority().getId()) ||
				!userRepo.existsById(source.getUser().getId()))
			throw new IllegalArgumentException();
		
		UserAuthority ua = new UserAuthority(source.getId(), 
				userRepo.getOne(source.getUser().getId()), 
				authorityRepo.getOne(source.getAuthority().getId()));
		
		return ua;
	}

}
