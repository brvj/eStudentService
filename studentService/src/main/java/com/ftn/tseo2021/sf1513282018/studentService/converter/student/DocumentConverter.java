package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.DocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Document;

@Component
public class DocumentConverter implements DtoConverter<Document, DocumentDTO , DefaultDocumentDTO> {

	@Override
	public Document convertToJPA(DocumentDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Document> convertToJPA(List<? extends DocumentDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends DocumentDTO> T convertToDTO(Document source, Class<? extends DocumentDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends DocumentDTO> convertToDTO(List<Document> sources, Class<? extends DocumentDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultDocumentDTO convertToDTO(Document source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultDocumentDTO> convertToDTO(List<Document> sources) {
		// TODO Auto-generated method stub
		return null;
	}


}
