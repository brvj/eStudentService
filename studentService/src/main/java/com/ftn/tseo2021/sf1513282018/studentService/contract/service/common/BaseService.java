package com.ftn.tseo2021.sf1513282018.studentService.contract.service.common;

import javax.persistence.EntityNotFoundException;

import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;

public interface BaseService<T, ID> {
	
//    public List<T> getAll();
	
//	public boolean existsById(ID id);
	
    public T getOne(ID id) throws PersonalizedAccessDeniedException;
    
    public ID create(T t) throws IllegalArgumentException, PersonalizedAccessDeniedException;
    
    public void update(ID id, T t) throws EntityNotFoundException, IllegalArgumentException, PersonalizedAccessDeniedException;
    
    public boolean delete(ID id) throws PersonalizedAccessDeniedException;
    
}
