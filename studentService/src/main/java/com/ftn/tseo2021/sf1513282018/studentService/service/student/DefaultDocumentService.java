package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.Optional;
import java.util.function.Function;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.DocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.DocumentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.DocumentService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Document;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeStudentOrAdmin;

@Service
public class DefaultDocumentService implements DocumentService {
	
	@Autowired
	DocumentRepository documentRepo;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	DtoConverter<Document, DocumentDTO, DefaultDocumentDTO> documentConverter;
	
	@Autowired
	private PersonalizedAuthorizator authorizator;

	@AuthorizeStudentOrAdmin
	@Override
	public DefaultDocumentDTO getOne(Integer id) {
		Optional<Document> doc = documentRepo.findById(id);
		return documentConverter.convertToDTO(doc.orElse(null));
	}

	@AuthorizeStudentOrAdmin
	@Override
	public Integer create(DefaultDocumentDTO dto) {
		Document doc = documentConverter.convertToJPA(dto);
		
		doc = documentRepo.save(doc);
		
		return doc.getId();
	}

	@AuthorizeStudentOrAdmin
	@Override
	public void update(Integer id, DefaultDocumentDTO dto) {
		if (!documentRepo.existsById(id)) throw new ResourceNotFoundException();
		
		Document dNew = documentConverter.convertToJPA(dto);
		
		Document d = documentRepo.findById(id).get();
		d.setName(dNew.getName());
		d.setType(dNew.getType());
		d.setPath(dNew.getPath());
		documentRepo.save(d);
		
	}

	@AuthorizeStudentOrAdmin
	@Override
	public void delete(Integer id) {
		if (!documentRepo.existsById(id)) {}
		documentRepo.deleteById(id);
	}

	@AuthorizeStudentOrAdmin
	@SuppressWarnings("unchecked")
	@Override
	public Page<StudentDocumentDTO> filterDocuments(int studentId, Pageable pageable, StudentDocumentDTO filterOptions) {
		Page<Document> page;

		if (filterOptions == null) {
			page = documentRepo.findByStudent_Id(studentId, pageable);
		}
		else {
			page = documentRepo.filterDocuments(studentId, filterOptions.getName(), pageable);
		}
		return page.map(new Function<Document, StudentDocumentDTO>() {
			@Override
			public StudentDocumentDTO apply(Document document) {
				return documentConverter.convertToDTO(document, StudentDocumentDTO.class);
			}
		});
	}
}
