package com.ftn.tseo2021.sf1513282018.studentService.converter.teacher;

import java.util.ArrayList;
import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherTitleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherTitleRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.AuthorityService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherTitleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultAuthorityDTO;
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
	
	@Autowired
	private AuthorityService authorityService;

	@Override
	public Teacher convertToJPA(TeacherDTO source) {
		if (source instanceof DefaultTeacherDTO) {
			return convertToJPA((DefaultTeacherDTO) source);
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
		else if (returnType == InstitutionTeacherDTO.class) {
			return (T) convertToInstitutionTeacherDTO(source);
		}
		else {
			throw new IllegalArgumentException(String.format(
					"Converting to %s type is not supported", returnType.toString()));
		}
	}
	
	@Override
	public List<? extends TeacherDTO> convertToDTO(List<Teacher> sources, Class<? extends TeacherDTO> returnType) {
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
	
	private Teacher convertToJPA(DefaultTeacherDTO source) {
		if (source == null || source.getInstitution() == null || source.getTeacherTitle() == null 
				|| source.getUser() == null
				|| !institutionRepo.existsById(source.getInstitution().getId())
				|| !teacherTitleRepo.existsById(source.getTeacherTitle().getId()))
			throw new EntityValidationException();
		
		source.getUser().setFirstName(source.getFirstName());
		source.getUser().setLastName(source.getLastName());
		source.getUser().setInstitution(source.getInstitution());
		
		if (source.getUser().getAuthorities() == null) source.getUser().setAuthorities(new ArrayList<DefaultAuthorityDTO>());
		
		try {
			DefaultAuthorityDTO teacherAuth = authorityService.getAuthorityByName("TEACHER");
			
			boolean teacherAuthorityExists = false;
			for (DefaultAuthorityDTO a : source.getUser().getAuthorities()) {
				if (a.getName().equals("STUDENT"))
					throw new EntityValidationException();
				
				if (a.getId() == teacherAuth.getId()) teacherAuthorityExists = true;
			}
			if (!teacherAuthorityExists) source.getUser().getAuthorities().add(teacherAuth);
		} catch (ResourceNotFoundException e) { throw new RuntimeException(); }

		User user = userConverter.convertToJPA(source.getUser());

		Teacher teacher = new Teacher();
//		teacher.setId(source.getId());
		teacher.setFirstName(source.getFirstName());
		teacher.setLastName(source.getLastName());
		teacher.setAddress(source.getAddress());
		teacher.setDateOfBirth(source.getDateOfBirth());
		teacher.setTeacherTitle(teacherTitleRepo.getOne(source.getTeacherTitle().getId()));
		teacher.setUser(user);
		teacher.setInstitution(institutionRepo.getOne(source.getInstitution().getId()));

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
