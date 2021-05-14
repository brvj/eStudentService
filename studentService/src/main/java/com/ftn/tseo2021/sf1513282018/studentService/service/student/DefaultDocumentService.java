package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
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

@Service
public class DefaultDocumentService implements DocumentService {
	
	@Autowired
	DocumentRepository documentRepo;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	DtoConverter<Document, DocumentDTO, DefaultDocumentDTO> documentConverter;

	@Override
	public DefaultDocumentDTO getOne(Integer id) {
		Optional<Document> doc = documentRepo.findById(id);
		return documentConverter.convertToDTO(doc.orElse(null));
	}

	@Override
	public Integer create(DefaultDocumentDTO dto) throws IllegalArgumentException {
		Document doc = documentConverter.convertToJPA(dto);
		
		doc = documentRepo.save(doc);
		
		return doc.getId();
	}

	@Override
	public void update(Integer id, DefaultDocumentDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if (!documentRepo.existsById(id)) throw new EntityNotFoundException();
		
		Document dNew = documentConverter.convertToJPA(dto);
		
		Document d = documentRepo.findById(id).get();
		d.setName(dNew.getName());
		d.setType(dNew.getType());
		d.setPath(dNew.getPath());
		documentRepo.save(d);
		
	}

	@Override
	public void delete(Integer id) {
		if (!documentRepo.existsById(id)) {}
		documentRepo.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentDocumentDTO> filterDocuments(int studentId, Pageable pageable, StudentDocumentDTO filterOptions) {
		if (filterOptions == null) {
			Page<Document> page = documentRepo.findByStudent_Id(studentId, pageable);
			return (List<StudentDocumentDTO>) documentConverter.convertToDTO(page.getContent(), StudentDocumentDTO.class);
		}
		else {
			Page<Document> page = documentRepo.filterDocuments(studentId, filterOptions.getName(), pageable);
			return (List<StudentDocumentDTO>) documentConverter.convertToDTO(page.getContent(), StudentDocumentDTO.class);
		}
	}

}
