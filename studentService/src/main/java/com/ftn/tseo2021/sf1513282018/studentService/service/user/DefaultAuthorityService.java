package com.ftn.tseo2021.sf1513282018.studentService.service.user;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.AuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.AuthorityRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.AuthorityService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserAuthorityService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserAuthorityDTO;
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
	public boolean existsById(Integer id) {
		return authorityRepo.existsById(id);
	}
	
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
		
//		REAL PUT
//		aNew.setId(id);
//		authorityRepo.save(aNew);
		
//		SIMULATE PATCH
		Authority a = authorityRepo.getOne(id);
		a.setName(aNew.getName());
		
	}

	@Override
	public boolean delete(Integer id) {
		if (!authorityRepo.existsById(id)) return false;
		return true;
	}

	@Override
	public List<DefaultUserAuthorityDTO> getAuthorityUserAuthorities(int authorityId, 
			Pageable pageable) throws EntityNotFoundException {
		if (!authorityRepo.existsById(authorityId)) throw new EntityNotFoundException();
		return userAuthorityService.getByAuthorityId(authorityId, pageable);
	}

}
