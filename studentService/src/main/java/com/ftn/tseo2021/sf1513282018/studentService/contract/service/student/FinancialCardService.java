package com.ftn.tseo2021.sf1513282018.studentService.contract.service.student;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;

public interface FinancialCardService extends BaseService<DefaultFinancialCardDTO, Integer> {
	
	DefaultFinancialCardDTO getByStudentId(int sutdentId);

}
