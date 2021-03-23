package com.ftn.tseo2021.sf1513282018.studentService.converter.institution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;

@Component
public class InstitutionConverter implements DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> {

	@Override
	public Institution convertToJPA(InstitutionDTO source) {
		if(source instanceof DefaultInstitutionDTO) return convertToJPA((DefaultInstitutionDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<Institution> convertToJPA(List<? extends InstitutionDTO> sources) {
		List<Institution> result = new ArrayList<>();

		if(sources != null){
			if(sources.get(0) instanceof DefaultInstitutionDTO){
				sources.stream().forEach(institutionDTO -> {
					result.add(convertToJPA((DefaultInstitutionDTO) institutionDTO));
				});
			}
			else throw new IllegalArgumentException(String.format("Converting from %s type is not supported!", sources.get(0).getClass().toString()));
		}
		return result;
	}

	@Override
	public <T extends InstitutionDTO> T convertToDTO(Institution source, Class<? extends InstitutionDTO> returnType) {
		if(returnType == DefaultInstitutionDTO.class) return (T) convertToDefaultInstitutionDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends InstitutionDTO> convertToDTO(List<Institution> sources, Class<? extends InstitutionDTO> returnType) {
		if(returnType == DefaultInstitutionDTO.class){
			List<DefaultInstitutionDTO> result = new ArrayList<>();
			sources.stream().forEach(institution -> {
				result.add(convertToDefaultInstitutionDTO(institution));
			});
			return result;

		}else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultInstitutionDTO convertToDTO(Institution source) {
		return convertToDefaultInstitutionDTO(source);
	}

	@Override
	public List<DefaultInstitutionDTO> convertToDTO(List<Institution> sources) {
		return (List<DefaultInstitutionDTO>) convertToDTO(sources, DefaultInstitutionDTO.class);
	}

	private DefaultInstitutionDTO convertToDefaultInstitutionDTO(Institution source){
		if(source == null) throw new NullPointerException();

		DefaultInstitutionDTO dto = new DefaultInstitutionDTO(source.getId(), source.getName(), source.getAddress(), source.getPhoneNumber());

		return dto;
	}

	private Institution convertToJPA(DefaultInstitutionDTO source){
		if(source == null) throw new NullPointerException();

		Institution institution = new Institution(source.getId(), source.getName(), source.getAddress(), source.getPhoneNumber(), new HashSet<>(), new HashSet<>(),
				new HashSet<>(), new HashSet<>(), new HashSet<>());

		return institution;
	}

}
