package com.ftn.tseo2021.sf1513282018.studentService.service.institution;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class DefaultInstitutionService implements InstitutionService {
	
	@Autowired
	private InstitutionRepository institutionRepo;

	@Autowired
	private DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> institutionConverter;

	@Override
	public DefaultInstitutionDTO getOne(Integer id) {
		Optional<Institution> institution = institutionRepo.findById(id);
		return institutionConverter.convertToDTO(institution.orElse(null));
	}

	@Override
	public Integer create(DefaultInstitutionDTO t) {
		Institution institution = institutionConverter.convertToJPA(t);

		institution = institutionRepo.save(institution);

		return institution.getId();
	}

	@Override
	public void update(Integer id, DefaultInstitutionDTO t) {
		if(!institutionRepo.existsById(id)) throw new EntityNotFoundException();

		t.setId(id);
		Institution institution = institutionConverter.convertToJPA(t);

		institutionRepo.save(institution);
	}

	@Override
	public boolean delete(Integer id) {
		if(!institutionRepo.existsById(id)) return false;

		institutionRepo.deleteById(id);
		return true;
	}
}
