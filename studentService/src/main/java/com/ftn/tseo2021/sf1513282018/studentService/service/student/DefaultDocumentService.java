package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.DocumentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.DocumentService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultDocumentDTO;

public class DefaultDocumentService implements DocumentService {
	
	@Autowired
	DocumentRepository documentRepo;

	@Override
	public DefaultDocumentDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(DefaultDocumentDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, DefaultDocumentDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
