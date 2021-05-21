package com.ftn.tseo2021.sf1513282018.studentService.controller.student;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.DocumentService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;

@RestController
@RequestMapping(value = "api/documents")
public class DocumentController {
	
	@Autowired
	DocumentService documentService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createDocument(@RequestBody DefaultDocumentDTO documentDTO) {
		int id = documentService.create(documentDTO);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateDocument(@PathVariable("id") int id, 
			@RequestBody DefaultDocumentDTO documentDTO) {
		documentService.update(id, documentDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteDocument(@PathVariable("id") int id) {
		documentService.delete(id); 
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultDocumentDTO> getDocumentById(@PathVariable("id") int id) {
		DefaultDocumentDTO document = documentService.getOne(id);
		
		return new ResponseEntity<>(document, HttpStatus.OK);
	}
	
}
