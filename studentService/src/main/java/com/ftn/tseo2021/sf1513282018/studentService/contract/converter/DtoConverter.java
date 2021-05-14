package com.ftn.tseo2021.sf1513282018.studentService.contract.converter;

import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;

public interface DtoConverter<JPA, DTO, DefaultDTO extends DTO> {
	
	/***
	 * 
	 * @throws IllegalArgumentException when converting for provided source type is not implemented
	 * @throws EntityValidationException when source is null or source is invalid
	 */
	JPA convertToJPA(DTO source);
	
	/***
	 * 
	 * @throws IllegalArgumentException when converting for provided source type is not implemented
	 * @throws EntityValidationException when sources is null or any of sources is invalid
	 */
	List<JPA> convertToJPA(List<? extends DTO> sources);
	
	<T extends DTO> T convertToDTO(JPA source, Class<? extends DTO> returnType);
	
	List<? extends DTO> convertToDTO(List<JPA> sources, Class<? extends DTO> returnType);
	
	DefaultDTO convertToDTO(JPA source);
	
	List<DefaultDTO> convertToDTO(List<JPA> sources);

}
