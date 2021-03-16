package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherRoleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherRoleRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherRoleService;

public class DefaultTeacherRoleService implements TeacherRoleService {
	
	@Autowired
	TeacherRoleRepository teacherRoleRepo;

	@Override
	public TeacherRoleDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(TeacherRoleDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, TeacherRoleDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
