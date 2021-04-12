package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.FinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.FinancialCardRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.UserRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.InstitutionStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
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
	public Student convertToJPA(StudentDTO source) throws IllegalArgumentException {
		if (source instanceof DefaultStudentDTO) return convertToJPA((DefaultStudentDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<Student> convertToJPA(List<? extends StudentDTO> sources) throws IllegalArgumentException {
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
	public <T extends StudentDTO> T convertToDTO(Student source, Class<? extends StudentDTO> returnType) throws IllegalArgumentException {
		if (returnType == DefaultStudentDTO.class) return (T) convertToDefaultStudentDTO(source);
		else if (returnType == InstitutionStudentDTO.class) return (T) convertToInstitutionStudentDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends StudentDTO> convertToDTO(List<Student> sources, Class<? extends StudentDTO> returnType) throws IllegalArgumentException {
		if (returnType == DefaultStudentDTO.class) {
			List<DefaultStudentDTO> result = new ArrayList<>();
			for (Student jpa : sources) result.add(convertToDefaultStudentDTO(jpa));
			return result;
		}
		else if (returnType == InstitutionStudentDTO.class) {
			List<InstitutionStudentDTO> result = new ArrayList<>();
			for (Student jpa : sources) result.add(convertToInstitutionStudentDTO(jpa));
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
				source.getLastName(), source.getStudentCard(), source.getAddress(), source.getGeneration(), source.getDateOfBirth(), 
				institutionConverter.convertToDTO(source.getInstitution()), 
				userConverter.convertToDTO(source.getUser()), 
				financialCardConverter.convertToDTO(source.getFinancialCard()));
		
		return dto;
	}
	
	private InstitutionStudentDTO convertToInstitutionStudentDTO(Student source) {
		if (source == null) return null;
		
		InstitutionStudentDTO dto = new InstitutionStudentDTO(source.getId(), source.getFirstName(), source.getLastName(), 
				source.getStudentCard(), source.getAddress(), source.getGeneration(), source.getDateOfBirth());
		
		return dto;
	}
	
	private Student convertToJPA(DefaultStudentDTO source) throws IllegalArgumentException {
		if (source == null) return null;
		
		if (source.getInstitution() == null || source.getUser() == null ||
				!institutionRepo.existsById(source.getInstitution().getId()))
			throw new IllegalArgumentException();
		
		Institution institution = institutionRepo.getOne(source.getInstitution().getId());
		DefaultInstitutionDTO iDTO = new DefaultInstitutionDTO();
		iDTO.setId(source.getInstitution().getId());
		source.getUser().setInstitution(iDTO);
		User user = userConverter.convertToJPA(source.getUser());
		
		
		Student student = new Student();
//		student.setId(source.getId());
		student.setFirstName(source.getFirstName());
		student.setLastName(source.getLastName());
		student.setStudentCard(source.getStudentCard());
		student.setAddress(source.getAddress());
		student.setDateOfBirth(source.getDateOfBirth());
		student.setGeneration(source.getGeneration());
		student.setInstitution(institution);
		student.setUser(user);
		FinancialCard fCard = new FinancialCard();
		fCard.setStudent(student);
		student.setFinancialCard(new FinancialCard());
		
		return student;
	}
	

}
