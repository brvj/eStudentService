package com.ftn.tseo2021.sf1513282018.studentService.converter.teacher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherRoleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherRoleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherRole;

@Component
public class TeacherRoleConverter implements DtoConverter<TeacherRole, TeacherRoleDTO, DefaultTeacherRoleDTO> {

	@Override
	public TeacherRole convertToJPA(TeacherRoleDTO source) throws IllegalArgumentException {
		if(source instanceof DefaultTeacherRoleDTO) return convertToJPA((DefaultTeacherRoleDTO) source);
		else throw new IllegalArgumentException(String.format("Converting from %s type is not supported!", source.getClass().toString()));
	}

	@Override
	public List<TeacherRole> convertToJPA(List<? extends TeacherRoleDTO> sources) throws IllegalArgumentException {
		List<TeacherRole> result = new ArrayList<>();

		if(sources != null){
			if(sources.get(0) instanceof DefaultTeacherRoleDTO){
				sources.stream().forEach(teacherRoleDTO -> {
					result.add(convertToJPA((DefaultTeacherRoleDTO) teacherRoleDTO));
				});
			}else throw new IllegalArgumentException(String.format("Converting from %s type is not supported!", sources.get(0).getClass().toString()));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends TeacherRoleDTO> T convertToDTO(TeacherRole source, Class<? extends TeacherRoleDTO> returnType) throws IllegalArgumentException {
		if(returnType == DefaultTeacherRoleDTO.class) return (T) convertToDefaultTeacherRoleDTO(source);
		else throw new IllegalArgumentException(String.format("Converting from %s type is not supported!", source.getClass().toString()));
	}

	@Override
	public List<? extends TeacherRoleDTO> convertToDTO(List<TeacherRole> sources, Class<? extends TeacherRoleDTO> returnType) throws IllegalArgumentException {
		if(returnType == DefaultTeacherRoleDTO.class){
			List<DefaultTeacherRoleDTO> result = new ArrayList<>();
			if(sources != null){
				sources.stream().forEach(teacherRole -> {
					result.add(convertToDefaultTeacherRoleDTO(teacherRole));
				});
			}
			return result;

		}else throw new IllegalArgumentException(String.format("Converting from %s type is not supported!", sources.get(0).getClass().toString()));
	}

	@Override
	public DefaultTeacherRoleDTO convertToDTO(TeacherRole source) {
		return convertToDefaultTeacherRoleDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultTeacherRoleDTO> convertToDTO(List<TeacherRole> sources) {
		return (List<DefaultTeacherRoleDTO>) convertToDTO(sources, DefaultTeacherRoleDTO.class);
	}

	private DefaultTeacherRoleDTO convertToDefaultTeacherRoleDTO(TeacherRole source) {
		if(source == null) return null;

		DefaultTeacherRoleDTO dto = new DefaultTeacherRoleDTO(source.getId(), source.getName());

		return dto;
	}

	private TeacherRole convertToJPA(DefaultTeacherRoleDTO source) throws IllegalArgumentException {
		if(source == null) return null;

		TeacherRole teacherRole = new TeacherRole();
//		teacherRole.setId(source.getId());
		teacherRole.setName(source.getName());
		
		return teacherRole;
	}

}
