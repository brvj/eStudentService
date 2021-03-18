package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.EnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;

@Component
public class EnrollmentConverter implements DtoConverter<Enrollment, EnrollmentDTO, DefaultEnrollmentDTO> {

	@Override
	public Enrollment convertToJPA(EnrollmentDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enrollment> convertToJPA(List<? extends EnrollmentDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends EnrollmentDTO> T convertToDTO(Enrollment source, Class<? extends EnrollmentDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends EnrollmentDTO> convertToDTO(List<Enrollment> sources,
			Class<? extends EnrollmentDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultEnrollmentDTO convertToDTO(Enrollment source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultEnrollmentDTO> convertToDTO(List<Enrollment> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
