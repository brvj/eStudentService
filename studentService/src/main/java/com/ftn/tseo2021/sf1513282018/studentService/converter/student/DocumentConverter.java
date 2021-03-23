package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.DocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.EnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.StudentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Document;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;

@Component
public class DocumentConverter implements DtoConverter<Document, DocumentDTO , DefaultDocumentDTO> {

	@Autowired
	DtoConverter<Student, StudentDTO, DefaultStudentDTO> studentConverter;
	
	@Autowired
	StudentRepository studentRepo;
	
	@Override
	public Document convertToJPA(DocumentDTO source) {
		if (source instanceof DefaultDocumentDTO) return convertToJPA((DefaultDocumentDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<Document> convertToJPA(List<? extends DocumentDTO> sources) {
		List<Document> result = new ArrayList<Document>();
		
		if (sources.get(0) instanceof DefaultDocumentDTO) {
			for (DocumentDTO dto : sources) result.add(convertToJPA((DefaultDocumentDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DocumentDTO> T convertToDTO(Document source, Class<? extends DocumentDTO> returnType) {
		if (returnType == DefaultDocumentDTO.class) return (T) convertToDefaultDocumentDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends DocumentDTO> convertToDTO(List<Document> sources, Class<? extends DocumentDTO> returnType) {
		if (returnType == DefaultDocumentDTO.class) {
			List<DefaultDocumentDTO> result = new ArrayList<DefaultDocumentDTO>();
			for (Document jpa : sources) result.add(convertToDefaultDocumentDTO(jpa));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultDocumentDTO convertToDTO(Document source) {
		return convertToDefaultDocumentDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultDocumentDTO> convertToDTO(List<Document> sources) {
		return (List<DefaultDocumentDTO>) convertToDTO(sources, DefaultDocumentDTO.class);
	}
	
	private DefaultDocumentDTO convertToDefaultDocumentDTO(Document source) {
		if (source == null) return null;
		
		DefaultDocumentDTO dto = new DefaultDocumentDTO(source.getId(), source.getName(), 
				source.getType(), source.getPath(),
				studentConverter.convertToDTO(source.getStudent()));
		
		return dto;
	}


}
