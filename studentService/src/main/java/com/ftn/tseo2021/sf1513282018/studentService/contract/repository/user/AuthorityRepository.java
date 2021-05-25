package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
	
	Optional<Authority> findByName(String name);
}
