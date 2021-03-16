package com.ftn.tseo2021.sf1513282018.studentService.contract.service.common;

import java.util.List;

public interface BaseService<T, ID> {
	
//    public List<T> getAll();
	
    public T getOne(ID id);
    
    public ID create(T t);
    
    public void update(ID id, T t);
    
    public boolean delete(ID id);
    
}
