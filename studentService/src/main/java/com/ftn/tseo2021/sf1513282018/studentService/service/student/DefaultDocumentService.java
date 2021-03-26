package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.DocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.DocumentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.StudentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.DocumentService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Document;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;

@Service
public class DefaultDocumentService implements DocumentService {
	
	@Autowired
	DocumentRepository documentRepo;
	
	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	DtoConverter<Document, DocumentDTO, DefaultDocumentDTO> documentConverter;

	@Override
	public DefaultDocumentDTO getOne(Integer id) {
		Optional<Document> doc = documentRepo.findById(id);
		return documentConverter.convertToDTO(doc.orElse(null));
	}

	@Override
	public Integer create(DefaultDocumentDTO t) {
		Document doc = documentConverter.convertToJPA(t);
		
		doc = documentRepo.save(doc);
		
		return doc.getId();
	}

	@Override
	public void update(Integer id, DefaultDocumentDTO t) {
		if (!documentRepo.existsById(id)) throw new EntityNotFoundException();
		
		t.setId(id);
		Document doc = documentConverter.convertToJPA(t);
		
		documentRepo.save(doc);
		
	}

	@Override
	public boolean delete(Integer id) {
		if (!documentRepo.existsById(id)) return false;
		documentRepo.deleteById(id);
		return true;
	}

	@Override
	public List<DefaultDocumentDTO> getByStudentId(int studentId, Pageable pageable) {
		if(!studentRepo.existsById(studentId)) throw new EntityNotFoundException();

		Page<Document> page = documentRepo.filterDocuments(studentId, null, pageable);

		return documentConverter.convertToDTO(page.getContent());
	}

}
