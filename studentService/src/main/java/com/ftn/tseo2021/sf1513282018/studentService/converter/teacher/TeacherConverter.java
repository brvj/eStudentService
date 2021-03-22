package com.ftn.tseo2021.sf1513282018.studentService.converter.teacher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.converter.user.UserConverter;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherTitleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherTitle;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.AnotherTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;


@Component
public class TeacherConverter implements DtoConverter<Teacher, TeacherDTO, DefaultTeacherDTO> {

	@Autowired
	private TeacherTitleConverter teacherTitleConverter;

	@Autowired
	private UserConverter userConverter;

	@Override
	public Teacher convertToJPA(TeacherDTO source) {
		if (source instanceof DefaultTeacherDTO) {
			return convertToJPA((DefaultTeacherDTO) source);
		}
		else if (source instanceof AnotherTeacherDTO) {
			return convertToJPA((AnotherTeacherDTO) source);
		}
		else {
			throw new IllegalArgumentException(String.format(
					"Converting from %s type is not supported", source.getClass().toString()));
		}
	}

	@Override
	public List<Teacher> convertToJPA(List<? extends TeacherDTO> sources) {
		List<Teacher> result = new ArrayList<Teacher>();
		
		if (sources.get(0) instanceof DefaultTeacherDTO) {
			for (TeacherDTO dto : sources) {
				result.add(convertToJPA((DefaultTeacherDTO) dto));
			}
			return result;
		}
		else if (sources.get(0) instanceof AnotherTeacherDTO) {
			for (TeacherDTO dto : sources) {
				result.add(convertToJPA((AnotherTeacherDTO) dto));
			}
			return result;
		}
		else {
			throw new IllegalArgumentException(String.format(
					"Converting from %s type is not supported", sources.get(0).getClass().toString()));
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends TeacherDTO> T convertToDTO(Teacher source, Class<? extends TeacherDTO> returnType) {
		if (returnType == DefaultTeacherDTO.class) {
			return (T) convertToDefaultTeacherDTO(source);
		}
		else if (returnType == AnotherTeacherDTO.class) {
			return (T) convertToAnotherTeacherDTO(source);
		}
		else {
			throw new IllegalArgumentException(String.format(
					"Converting to %s type is not supported", returnType.toString()));
		}
	}
	
	@Override
	public List<? extends TeacherDTO> convertToDTO(List<Teacher> sources, Class<? extends TeacherDTO> returnType) {
		if (returnType == DefaultTeacherDTO.class) {
			List<DefaultTeacherDTO> result = new ArrayList<DefaultTeacherDTO>();
			for (Teacher jpa : sources) {
				result.add(convertToDefaultTeacherDTO(jpa));
			}
			return result;
		}
		else if (returnType == AnotherTeacherDTO.class) {
			List<AnotherTeacherDTO> result = new ArrayList<AnotherTeacherDTO>();
			for (Teacher jpa : sources) {
				result.add(convertToAnotherTeacherDTO(jpa));
			}
			return result;
		}
		else {
			throw new IllegalArgumentException(String.format(
					"Converting to %s type is not supported", returnType.toString()));
		}
	}

	@Override
	public DefaultTeacherDTO convertToDTO(Teacher source) {
		return convertToDefaultTeacherDTO(source);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultTeacherDTO> convertToDTO(List<Teacher> sources) {
		return (List<DefaultTeacherDTO>)convertToDTO(sources, DefaultTeacherDTO.class);
	}
	
	private Teacher convertToJPA(DefaultTeacherDTO source) {
		if(source == null) throw new NullPointerException();

		//Teaching and Institution converters have not been implemented yet
		TeacherTitle teacherTitle = teacherTitleConverter.convertToJPA(source.getTeacherTitle());
		User user = userConverter.convertToJPA(source.getUser());
		Teacher teacher = new Teacher(source.getId(), source.getFirstName(), source.getLastName(), source.getAddress(), source.getDateOfBirth(),
				teacherTitle, user, new HashSet<>(), new Institution());

		return teacher;
	}
	
	private Teacher convertToJPA(AnotherTeacherDTO source) {
		return null;
	}
	
	private DefaultTeacherDTO convertToDefaultTeacherDTO(Teacher source) {
		if(source == null) throw new NullPointerException();

		//Teaching and Institution converters have not been implemented yet
		DefaultTeacherTitleDTO teacherTitleDTO = (source.getTeacherTitle() != null)? teacherTitleConverter.convertToDTO(source.getTeacherTitle()) : null;
		DefaultUserDTO userDTO = (source.getUser() != null)? userConverter.convertToDTO(source.getUser()) : null;
		DefaultTeacherDTO dto = new DefaultTeacherDTO(source.getId(), source.getFirstName(), source.getLastName(), source.getAddress(), source.getDateOfBirth(),
				teacherTitleDTO, userDTO, new ArrayList<>(), new DefaultInstitutionDTO());

		return dto;
	}
	
	private AnotherTeacherDTO convertToAnotherTeacherDTO(Teacher source) {
		return null;
	}

}
