package com.ftn.tseo2021.sf1513282018.studentService.contract.service.common;

public interface BaseService<T, ID> {
	
//    public List<T> getAll();
	
//	public boolean existsById(ID id);
	
    public T getOne(ID id);
    
    public ID create(T t);
    
    public void update(ID id, T t);
    
    public void delete(ID id);
    
}
