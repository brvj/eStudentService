package com.ftn.tseo2021.sf1513282018.studentService.service.user;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.AuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.AuthorityRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.AuthorityService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserAuthorityService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.Authority;

@Service
public class DefaultAuthorityService implements AuthorityService {
	
	@Autowired
	AuthorityRepository authorityRepo;
	
	@Autowired
	DtoConverter<Authority, AuthorityDTO, DefaultAuthorityDTO> authorityConverter;

	@Autowired
	UserAuthorityService userAuthorityService;
	
	@Override
	public DefaultAuthorityDTO getOne(Integer id) {
		Optional<Authority> a = authorityRepo.findById(id);
		return authorityConverter.convertToDTO(a.orElse(null));
	}

	@Override
	public Integer create(DefaultAuthorityDTO dto) throws IllegalArgumentException {
		Authority a = authorityConverter.convertToJPA(dto);
		
		a = authorityRepo.save(a);
		
		return a.getId();
	}

	@Override
	public void update(Integer id, DefaultAuthorityDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if (!authorityRepo.existsById(id)) throw new EntityNotFoundException();
		
		Authority aNew = authorityConverter.convertToJPA(dto);
		
		Authority a = authorityRepo.findById(id).get();
		a.setName(aNew.getName());
		authorityRepo.save(a);
	}

	@Override
	public void delete(Integer id) {
		if (!authorityRepo.existsById(id)) {}
	}

	@Override
	public DefaultAuthorityDTO getAuthorityByName(String name) {
		Authority a = authorityRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException());
		return authorityConverter.convertToDTO(a);
	}

}
