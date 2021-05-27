package com.ftn.tseo2021.sf1513282018.studentService.contract.service.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.FinancialCardTransactionDTO;

public interface FinancialCardService extends BaseService<DefaultFinancialCardDTO, Integer> {
	
	DefaultFinancialCardDTO getByStudentId(int sutdentId);
	
	Page<FinancialCardTransactionDTO> getFinancialCardTransactions(int cardId, Pageable pageable);

}
