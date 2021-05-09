package com.ftn.tseo2021.sf1513282018.studentService.contract.service.common;

import javax.persistence.EntityNotFoundException;

import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ForbiddenAccessException;

public interface BaseService<T, ID> {
	
//    public List<T> getAll();
	
//	public boolean existsById(ID id);
	
    public T getOne(ID id) throws ForbiddenAccessException;
    
    public ID create(T t) throws IllegalArgumentException, ForbiddenAccessException;
    
    public void update(ID id, T t) throws EntityNotFoundException, IllegalArgumentException, ForbiddenAccessException;
    
    public boolean delete(ID id) throws ForbiddenAccessException;
    
}
