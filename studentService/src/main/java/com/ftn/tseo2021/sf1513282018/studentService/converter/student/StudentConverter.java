package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;

@Component
public class StudentConverter implements DtoConverter<Student, StudentDTO, DefaultStudentDTO> {

	@Override
	public Student convertToJPA(StudentDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> convertToJPA(List<? extends StudentDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends StudentDTO> T convertToDTO(Student source, Class<? extends StudentDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends StudentDTO> convertToDTO(List<Student> sources, Class<? extends StudentDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultStudentDTO convertToDTO(Student source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultStudentDTO> convertToDTO(List<Student> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
