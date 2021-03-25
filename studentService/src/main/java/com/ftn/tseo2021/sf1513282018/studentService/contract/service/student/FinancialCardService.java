package com.ftn.tseo2021.sf1513282018.studentService.contract.service.student;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultTransactionDTO;

public interface FinancialCardService extends BaseService<DefaultFinancialCardDTO, Integer> {
	
	DefaultFinancialCardDTO getByStudentId(int sutdentId);
	
	List<DefaultTransactionDTO> getFinancialCardTransactions(int cardId, Pageable pageable) throws EntityNotFoundException;

}
