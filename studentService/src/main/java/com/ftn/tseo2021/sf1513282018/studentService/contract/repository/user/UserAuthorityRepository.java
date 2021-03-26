package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Integer> {
	
	Page<UserAuthority> findByUser_id(int userId, Pageable pageable);
	
	Page<UserAuthority> findByAuthority_id(int authorityId, Pageable pageable);

}
