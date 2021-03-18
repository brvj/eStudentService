package com.ftn.tseo2021.sf1513282018.studentService.converter.teacher;

import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherRoleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherRoleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherRole;

public class TeacherRoleConverter implements DtoConverter<TeacherRole, TeacherRoleDTO, DefaultTeacherRoleDTO> {

	@Override
	public TeacherRole convertToJPA(TeacherRoleDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeacherRole> convertToJPA(List<? extends TeacherRoleDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends TeacherRoleDTO> T convertToDTO(TeacherRole source, Class<? extends TeacherRoleDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends TeacherRoleDTO> convertToDTO(List<TeacherRole> sources,
			Class<? extends TeacherRoleDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultTeacherRoleDTO convertToDTO(TeacherRole source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultTeacherRoleDTO> convertToDTO(List<TeacherRole> sources) {
		// TODO Auto-generated method stub
		return null;
	}


}
