package com.ftn.tseo2021.sf1513282018.studentService.converter.institution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ftn.tseo2021.sf1513282018.studentService.converter.teacher.TeacherConverter;
import com.ftn.tseo2021.sf1513282018.studentService.converter.user.UserConverter;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;

@Component
public class InstitutionConverter implements DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> {

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private TeacherConverter teacherConverter;

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
			else {
				throw new IllegalArgumentException(String.format("Converting from %s type is not supported!", sources.get(0).getClass().toString()));
			}
		}
		return result;
	}

	@Override
	public <T extends InstitutionDTO> T convertToDTO(Institution source, Class<? extends InstitutionDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends InstitutionDTO> convertToDTO(List<Institution> sources,
			Class<? extends InstitutionDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultInstitutionDTO convertToDTO(Institution source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultInstitutionDTO> convertToDTO(List<Institution> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	private Institution convertToJPA(DefaultInstitutionDTO source){
		if(source == null) throw new NullPointerException();

		//Student, ExamPeriod and Course converters have not been implemented yet
		Set<User> users = (Set<User>) userConverter.convertToJPA(source.getUsers());
		Set<Teacher> teachers = (Set<Teacher>) teacherConverter.convertToJPA(source.getTeachers());
		Institution institution = new Institution(source.getId(), source.getName(), source.getAddress(), source.getPhoneNumber(), users, teachers,
				new HashSet<>(), new HashSet<>(), new HashSet<>());

		return institution;
	}

}
