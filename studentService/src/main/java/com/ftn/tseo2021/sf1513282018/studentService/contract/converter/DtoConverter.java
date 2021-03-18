package com.ftn.tseo2021.sf1513282018.studentService.contract.converter;

import java.util.List;

public interface DtoConverter<JPA, DTO, DefaultDTO extends DTO> {
	
	JPA convertToJPA(DTO source);
	
	List<JPA> convertToJPA(List<? extends DTO> sources);
	
	<T extends DTO> T convertToDTO(JPA source, Class<? extends DTO> returnType);
	
	List<? extends DTO> convertToDTO(List<JPA> sources, Class<? extends DTO> returnType);
	
	DefaultDTO convertToDTO(JPA source);
	
	List<DefaultDTO> convertToDTO(List<JPA> sources);

}
