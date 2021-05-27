package com.ftn.tseo2021.sf1513282018.studentService.contract.service.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultTransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.FinancialCardTransactionDTO;

public interface TransactionService extends BaseService<DefaultTransactionDTO, Integer> {
	
	Page<FinancialCardTransactionDTO> filterTransactions(int financialCardId, Pageable pageable, FinancialCardTransactionDTO filterOptions);
}
