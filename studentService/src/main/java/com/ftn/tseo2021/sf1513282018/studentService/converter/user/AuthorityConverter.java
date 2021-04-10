package com.ftn.tseo2021.sf1513282018.studentService.converter.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.AuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.Authority;

@Component
public class AuthorityConverter implements DtoConverter<Authority, AuthorityDTO, DefaultAuthorityDTO> {

	@Override
	public Authority convertToJPA(AuthorityDTO source) throws IllegalArgumentException {
		if (source instanceof DefaultAuthorityDTO) return convertToJPA((DefaultAuthorityDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<Authority> convertToJPA(List<? extends AuthorityDTO> sources) throws IllegalArgumentException {
		List<Authority> result = new ArrayList<>();
		
		if (sources.get(0) instanceof DefaultAuthorityDTO) {
			for (AuthorityDTO dto : sources) result.add(convertToJPA((DefaultAuthorityDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AuthorityDTO> T convertToDTO(Authority source, Class<? extends AuthorityDTO> returnType) throws IllegalArgumentException {
		if (returnType == DefaultAuthorityDTO.class) return (T) convertToDefaultAuthorityDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends AuthorityDTO> convertToDTO(List<Authority> sources,
			Class<? extends AuthorityDTO> returnType) throws IllegalArgumentException {
		if (returnType == DefaultAuthorityDTO.class) {
			List<DefaultAuthorityDTO> result = new ArrayList<>();
			for (Authority jpa : sources) result.add(convertToDefaultAuthorityDTO(jpa));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultAuthorityDTO convertToDTO(Authority source) {
		return convertToDefaultAuthorityDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultAuthorityDTO> convertToDTO(List<Authority> sources) {
		return (List<DefaultAuthorityDTO>) convertToDTO(sources, DefaultAuthorityDTO.class);
	}
	
	private DefaultAuthorityDTO convertToDefaultAuthorityDTO(Authority source) {
		if (source == null) return null;
		
		DefaultAuthorityDTO dto = new DefaultAuthorityDTO(source.getId(), source.getName());
		
		return dto;
	}
	
	private Authority convertToJPA(DefaultAuthorityDTO source) {
		if (source == null) return null;
		
		Authority a = new Authority();
		a.setId(source.getId());
		a.setName(source.getName());
		
		return a;
	}

}
