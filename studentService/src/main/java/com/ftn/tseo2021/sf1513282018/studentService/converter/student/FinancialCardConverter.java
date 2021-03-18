package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.FinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.FinancialCard;

@Component
public class FinancialCardConverter implements DtoConverter<FinancialCard, FinancialCardDTO, DefaultFinancialCardDTO> {

	@Override
	public FinancialCard convertToJPA(FinancialCardDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FinancialCard> convertToJPA(List<? extends FinancialCardDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends FinancialCardDTO> T convertToDTO(FinancialCard source,
			Class<? extends FinancialCardDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends FinancialCardDTO> convertToDTO(List<FinancialCard> sources,
			Class<? extends FinancialCardDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultFinancialCardDTO convertToDTO(FinancialCard source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultFinancialCardDTO> convertToDTO(List<FinancialCard> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
