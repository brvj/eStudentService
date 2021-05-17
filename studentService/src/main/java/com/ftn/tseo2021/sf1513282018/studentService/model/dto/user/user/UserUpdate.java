package com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.user;

public class UserUpdate extends BaseUser
		implements com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserUpdate {

	public UserUpdate(String username, String firstName, String lastName, String email, String phoneNumber) {
		super(username, firstName, lastName, email, phoneNumber);
	}

}
