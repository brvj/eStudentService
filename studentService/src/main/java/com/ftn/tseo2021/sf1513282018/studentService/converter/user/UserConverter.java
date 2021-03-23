package com.ftn.tseo2021.sf1513282018.studentService.converter.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;
import com.ftn.tseo2021.sf1513282018.studentService.model.common.UserType;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;

public class UserConverter implements DtoConverter<User, UserDTO, DefaultUserDTO> {
	
	@Autowired
	InstitutionRepository institutionRepo;
	
	@Autowired
	DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> institutionConverter;

	@Override
	public User convertToJPA(UserDTO source) {
		if (source instanceof DefaultUserDTO) return convertToJPA((DefaultUserDTO) source);
		else throw new IllegalArgumentException(String.format(
					"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<User> convertToJPA(List<? extends UserDTO> sources) {
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
	public <T extends UserDTO> T convertToDTO(User source, Class<? extends UserDTO> returnType) {
		if (returnType == DefaultUserDTO.class) return (T) convertToDefaultUserDTO(source);
		else throw new IllegalArgumentException(String.format(
					"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends UserDTO> convertToDTO(List<User> sources, Class<? extends UserDTO> returnType) {
		if (returnType == DefaultUserDTO.class) {
			List<DefaultUserDTO> result = new ArrayList<DefaultUserDTO>();
			for (User jpa : sources) {
				result.add(convertToDefaultUserDTO(jpa));
			}
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
		
		DefaultUserDTO dto = new DefaultUserDTO(source.getId(), source.getUsername(), "", 
				source.getFirstName(), source.getLastName(), source.getEmail(), source.getPhoneNumber(), 
				source.getUserType().toString(), institutionConverter.convertToDTO(source.getInstitution()));
		
		return dto;
	}
	
	private User convertToJPA(DefaultUserDTO source) {
		if (source == null) return null;
		
		if (!institutionRepo.existsById(source.getInstitution().getId()))
			throw new IllegalArgumentException();
		
		User user = new User(source.getId(), source.getUsername(), source.getPassword(), 
				source.getFirstName(), source.getLastName(), source.getEmail(), source.getPhoneNumber(), 
				UserType.valueOf(source.getUserType()), 
				institutionRepo.findById(source.getInstitution().getId()).get());
		
		return user;
	}

}
