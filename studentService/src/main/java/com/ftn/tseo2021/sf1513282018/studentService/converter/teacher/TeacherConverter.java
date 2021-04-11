package com.ftn.tseo2021.sf1513282018.studentService.converter.teacher;

import java.util.ArrayList;
import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherTitleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherTitleRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherTitleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherTitle;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.InstitutionTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;


@Component
public class TeacherConverter implements DtoConverter<Teacher, TeacherDTO, DefaultTeacherDTO> {

	@Autowired
	private DtoConverter<TeacherTitle, TeacherTitleDTO, DefaultTeacherTitleDTO> teacherTitleConverter;

	@Autowired
	private DtoConverter<User, UserDTO, DefaultUserDTO> userConverter;

	@Autowired
	private DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> institutionConverter;

	@Autowired
	private TeacherTitleRepository teacherTitleRepo;

	@Autowired
	private InstitutionRepository institutionRepo;

	@Override
	public Teacher convertToJPA(TeacherDTO source) throws IllegalArgumentException {
		if (source instanceof DefaultTeacherDTO) {
			return convertToJPA((DefaultTeacherDTO) source);
		}
		else {
			throw new IllegalArgumentException(String.format(
					"Converting from %s type is not supported", source.getClass().toString()));
		}
	}

	@Override
	public List<Teacher> convertToJPA(List<? extends TeacherDTO> sources) throws IllegalArgumentException {
		List<Teacher> result = new ArrayList<Teacher>();
		
		if (sources.get(0) instanceof DefaultTeacherDTO) {
			for (TeacherDTO dto : sources) {
				result.add(convertToJPA((DefaultTeacherDTO) dto));
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
	public <T extends TeacherDTO> T convertToDTO(Teacher source, Class<? extends TeacherDTO> returnType) throws IllegalArgumentException {
		if (returnType == DefaultTeacherDTO.class) {
			return (T) convertToDefaultTeacherDTO(source);
		}
		else if (returnType == InstitutionTeacherDTO.class) {
			return (T) convertToInstitutionTeacherDTO(source);
		}
		else {
			throw new IllegalArgumentException(String.format(
					"Converting to %s type is not supported", returnType.toString()));
		}
	}
	
	@Override
	public List<? extends TeacherDTO> convertToDTO(List<Teacher> sources, Class<? extends TeacherDTO> returnType) throws IllegalArgumentException {
		if (returnType == DefaultTeacherDTO.class) {
			List<DefaultTeacherDTO> result = new ArrayList<>();
			for (Teacher jpa : sources) {
				result.add(convertToDefaultTeacherDTO(jpa));
			}
			return result;
		}
		else if (returnType == InstitutionTeacherDTO.class) {
			List<InstitutionTeacherDTO> result = new ArrayList<>();
			for (Teacher jpa : sources) {
				result.add(convertToInstitutionTeacherDTO(jpa));
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
	
	private Teacher convertToJPA(DefaultTeacherDTO source) throws IllegalArgumentException {
		if (source == null) return null;
		
		if (source.getInstitution() == null || source.getTeacherTitle() == null 
				|| source.getUser() == null
				|| !institutionRepo.existsById(source.getInstitution().getId())
				|| !teacherTitleRepo.existsById(source.getTeacherTitle().getId()))
			throw new IllegalArgumentException();


		Institution institution = institutionRepo.getOne(source.getInstitution().getId());
		TeacherTitle teacherTitle = teacherTitleRepo.getOne(source.getTeacherTitle().getId());
		DefaultInstitutionDTO iDTO = new DefaultInstitutionDTO();
		iDTO.setId(source.getInstitution().getId());
		source.getUser().setInstitution(iDTO);
		User user = userConverter.convertToJPA(source.getUser());

		Teacher teacher = new Teacher();
//		teacher.setId(source.getId());
		teacher.setFirstName(source.getFirstName());
		teacher.setLastName(source.getLastName());
		teacher.setAddress(source.getAddress());
		teacher.setDateOfBirth(source.getDateOfBirth());
		teacher.setTeacherTitle(teacherTitle);
		teacher.setUser(user);
		teacher.setInstitution(institution);

		return teacher;
	}
	private DefaultTeacherDTO convertToDefaultTeacherDTO(Teacher source) {
		if(source == null) return null;

		DefaultTeacherDTO dto = new DefaultTeacherDTO(source.getId(), source.getFirstName(), source.getLastName(), 
				source.getAddress(), source.getDateOfBirth(), teacherTitleConverter.convertToDTO(source.getTeacherTitle()), 
				userConverter.convertToDTO(source.getUser()), institutionConverter.convertToDTO(source.getInstitution()));

		return dto;
	}
	
	private InstitutionTeacherDTO convertToInstitutionTeacherDTO(Teacher source) {
		if (source == null) return null;
		
		InstitutionTeacherDTO dto = new InstitutionTeacherDTO(source.getId(), source.getFirstName(), source.getLastName(), source.getAddress(), 
				source.getDateOfBirth(), teacherTitleConverter.convertToDTO(source.getTeacherTitle()));
		
		return dto;
	}
	
}
