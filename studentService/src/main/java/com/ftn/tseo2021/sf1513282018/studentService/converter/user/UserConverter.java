package com.ftn.tseo2021.sf1513282018.studentService.converter.user;

import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;

public class UserConverter implements DtoConverter<User, UserDTO, DefaultUserDTO> {

	@Override
	public User convertToJPA(UserDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> convertToJPA(List<? extends UserDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends UserDTO> T convertToDTO(User source, Class<? extends UserDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends UserDTO> convertToDTO(List<User> sources, Class<? extends UserDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultUserDTO convertToDTO(User source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultUserDTO> convertToDTO(List<User> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
