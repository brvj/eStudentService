package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.EnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.FinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.FinancialCardRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.UserRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamObligationTaking;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamTaking;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.FinancialCard;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;

@Component
public class StudentConverter implements DtoConverter<Student, StudentDTO, DefaultStudentDTO> {

	@Autowired
	DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> institutionConverter;
	
	@Autowired
	DtoConverter<User, UserDTO, DefaultUserDTO> userConverter;
	
	@Autowired
	DtoConverter<FinancialCard, FinancialCardDTO, DefaultFinancialCardDTO> financialCardConverter;
	
	@Autowired
	InstitutionRepository institutionRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	FinancialCardRepository financialCardRepo;
	
	
	@Override
	public Student convertToJPA(StudentDTO source) {
		if (source instanceof DefaultStudentDTO) return convertToJPA((DefaultStudentDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<Student> convertToJPA(List<? extends StudentDTO> sources) {
		List<Student> result = new ArrayList<Student>();
		
		if (sources.get(0) instanceof DefaultStudentDTO) {
			for (StudentDTO dto : sources) result.add(convertToJPA((DefaultStudentDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends StudentDTO> T convertToDTO(Student source, Class<? extends StudentDTO> returnType) {
		if (returnType == DefaultStudentDTO.class) return (T) convertToDefaultStudentDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends StudentDTO> convertToDTO(List<Student> sources, Class<? extends StudentDTO> returnType) {
		if (returnType == DefaultStudentDTO.class) {
			List<DefaultStudentDTO> result = new ArrayList<DefaultStudentDTO>();
			for (Student jpa : sources) result.add(convertToDefaultStudentDTO(jpa));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultStudentDTO convertToDTO(Student source) {
		return convertToDefaultStudentDTO(source);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultStudentDTO> convertToDTO(List<Student> sources) {
		return (List<DefaultStudentDTO>) convertToDTO(sources, DefaultStudentDTO.class);
	}
	
	private DefaultStudentDTO convertToDefaultStudentDTO(Student source) {
		if (source == null) return null;
		
		DefaultStudentDTO dto = new DefaultStudentDTO(source.getId(), source.getFirstName(), 
				source.getLastName(), source.getStudentCard(), source.getAddress(), source.getGeneration(), 
				institutionConverter.convertToDTO(source.getInstitution()), 
				userConverter.convertToDTO(source.getUser()), 
				financialCardConverter.convertToDTO(source.getFinancialCard()));
		
		return dto;
	}
	

}
