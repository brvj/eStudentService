package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.EnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.FinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultTransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.FinancialCard;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Transaction;

@Component
public class FinancialCardConverter implements DtoConverter<FinancialCard, FinancialCardDTO, DefaultFinancialCardDTO> {
	
	
	
	@Override
	public FinancialCard convertToJPA(FinancialCardDTO source) {
		if (source instanceof DefaultFinancialCardDTO) return convertToJPA((DefaultFinancialCardDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<FinancialCard> convertToJPA(List<? extends FinancialCardDTO> sources) {
		List<FinancialCard> result = new ArrayList<FinancialCard>();
		
		if (sources.get(0) instanceof DefaultFinancialCardDTO) {
			for (FinancialCardDTO dto : sources) result.add(convertToJPA((DefaultFinancialCardDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends FinancialCardDTO> T convertToDTO(FinancialCard source,
			Class<? extends FinancialCardDTO> returnType) {
		if (returnType == DefaultFinancialCardDTO.class) return (T) convertToDefaultFinancialCardDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends FinancialCardDTO> convertToDTO(List<FinancialCard> sources,
			Class<? extends FinancialCardDTO> returnType) {
		if (returnType == DefaultFinancialCardDTO.class) {
			List<DefaultFinancialCardDTO> result = new ArrayList<DefaultFinancialCardDTO>();
			for (FinancialCard jpa : sources) result.add(convertToDefaultFinancialCardDTO(jpa));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultFinancialCardDTO convertToDTO(FinancialCard source) {
		return convertToDefaultFinancialCardDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultFinancialCardDTO> convertToDTO(List<FinancialCard> sources) {
		return (List<DefaultFinancialCardDTO>) convertToDTO(sources, DefaultFinancialCardDTO.class);
	}

	private DefaultFinancialCardDTO convertToDefaultFinancialCardDTO(FinancialCard source) {
		if (source == null) return null;
		
		DefaultFinancialCardDTO dto = new DefaultFinancialCardDTO(source.getId(), source.getCurrentAmmout(), 
				source.getTotalDeposit(), source.getTotalSpent());
		
		return dto;
	}
	
	private FinancialCard convertToJPA(DefaultFinancialCardDTO source) {
		if (source == null) return null;
		
		FinancialCard financialCard = new FinancialCard(source.getId(), source.getCurrentAmmount(), source.getTotalDeposit(), 
				source.getTotalSpent(), new Student(), 
				new HashSet<Transaction>());
		
		return financialCard;
	}
}
