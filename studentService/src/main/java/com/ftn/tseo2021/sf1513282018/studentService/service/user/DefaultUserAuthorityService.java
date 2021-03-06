package com.ftn.tseo2021.sf1513282018.studentService.service.user;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.UserAuthorityRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserAuthorityService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.UserUserAuthorityDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.UserAuthority;

@Service
public class DefaultUserAuthorityService implements UserAuthorityService {
	
	@Autowired
	UserAuthorityRepository userAuthorityRepo;
	
	@Autowired
	DtoConverter<UserAuthority, UserAuthorityDTO, DefaultUserAuthorityDTO> userAuthorityConverter;

	@Override
	public DefaultUserAuthorityDTO getOne(Integer id) {
		Optional<UserAuthority> ua = userAuthorityRepo.findById(id);
		return userAuthorityConverter.convertToDTO(ua.orElse(null));
	}

	@Override
	public Integer create(DefaultUserAuthorityDTO dto) throws IllegalArgumentException {
		UserAuthority ua = userAuthorityConverter.convertToJPA(dto);
		
		ua = userAuthorityRepo.save(ua);
		
		return ua.getId();
	}

	@Override
	public void update(Integer id, DefaultUserAuthorityDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if (!userAuthorityRepo.existsById(id)) throw new EntityNotFoundException();
		
		UserAuthority uaNew = userAuthorityConverter.convertToJPA(dto);
		
//		UserAuthority ua = userAuthorityRepo.findById(id).get();
//		Nothing to change...
//		userAuthorityRepo.save(ua);
		
	}

	@Override
	public void delete(Integer id) {
		if (!userAuthorityRepo.existsById(id)) {}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserUserAuthorityDTO> getByUserId(int userId, Pageable pageable) {
		Page<UserAuthority> page = userAuthorityRepo.findByUser_id(userId, pageable);
		return (List<UserUserAuthorityDTO>) userAuthorityConverter.convertToDTO(page.getContent(), UserUserAuthorityDTO.class);
	}

}
