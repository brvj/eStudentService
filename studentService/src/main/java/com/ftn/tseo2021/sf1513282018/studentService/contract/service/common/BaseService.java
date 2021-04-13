package com.ftn.tseo2021.sf1513282018.studentService.contract.service.common;

import javax.persistence.EntityNotFoundException;

public interface BaseService<T, ID> {
	
//    public List<T> getAll();
	
	public boolean existsById(ID id);
	
    public T getOne(ID id);
    
    public ID create(T t) throws IllegalArgumentException;
    
    public void update(ID id, T t) throws EntityNotFoundException, IllegalArgumentException;
    
    public boolean delete(ID id);
    
}
