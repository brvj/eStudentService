package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByUsername(String username);
	
	@Query("SELECT u from User u WHERE "
			+ "u.institution.id = :institutionId AND "
			+ "(:username is null OR u.username LIKE CONCAT('%', :username, '%')) AND "
			+ "(:firstName is null OR lower(u.firstName) LIKE lower(CONCAT('%', :firstName, '%'))) AND "
			+ "(:lastName is null OR lower(u.lastName) LIKE lower(CONCAT('%', :lastName, '%'))) AND "
			+ "(:email is null OR lower(u.email) LIKE lower(CONCAT('%', :email, '%')))")
	Page<User> filterUsers(@Param("institutionId") int institutionId, @Param("username")String username, 
							@Param("firstName") String firstName, @Param("lastName") String lastName, 
							@Param("email") String email, Pageable pageable);
	
}
