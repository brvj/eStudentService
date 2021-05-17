package com.ftn.tseo2021.sf1513282018.studentService.contract.service.common;

public interface NewBaseService<ViewDTO, ID, CreateDTO, UpdateDTO> {
	
	public ViewDTO getOne(ID id);
    
    public ID create(CreateDTO t);
    
    public void update(ID id, UpdateDTO t);
    
    public void delete(ID id);

}
