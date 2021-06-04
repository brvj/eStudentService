package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

	Optional<Student> findByUser_Id(int userId);
	
	boolean existsByStudentCard(String studentCard);
	
	Page<Student> findByInstitution_Id(int institutionId, Pageable pageable);
	
	@Query("SELECT s from Student s WHERE " +
			"s.institution.id = :institutionId AND " +
			"(:firstName is null OR lower(s.firstName) LIKE lower(CONCAT('%', :firstName,'%'))) AND " +
			"(:lastName is null OR lower(s.lastName) LIKE lower(CONCAT('%', :lastName,'%'))) AND " +
			"(:studentCard is null OR lower(s.studentCard) LIKE lower(CONCAT('%', :studentCard,'%'))) AND " +
			"(:address is null OR lower(s.address) LIKE lower(CONCAT('%', :address,'%'))) AND " +
			"(:startBirthDate is null OR s.dateOfBirth >= :startBirthDate) AND " + 
			"(:endBirthDate is null OR s.dateOfBirth <= :endBirthDate) AND " +
			"(:startGeneration is null OR s.generation >= :startGeneration) AND " + 
			"(:endGeneration is null OR s.generation <= :endGeneration) AND " +
			"(:username is null OR lower(s.user.username) LIKE lower(CONCAT('%', :username, '%'))) AND " +
			"(:email is null OR lower(s.user.email) LIKE lower(CONCAT('%', :email, '%'))) AND " +
			"(:phoneNumber is null OR lower(s.user.phoneNumber) LIKE lower(CONCAT('%', :phoneNumber, '%')))")
	Page<Student> filterStudent(@Param("institutionId") int institutionId,
            @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("studentCard") String studentCard,
            @Param("address") String address, @Param("startBirthDate") LocalDate startBirthDate, @Param("endBirthDate") LocalDate endBirthDate,
            @Param("startGeneration") Integer startGeneration, @Param("endGeneration") Integer endGeneration, 
            @Param("username") String username, @Param("email") String email, @Param("phoneNumber") String phoneNumber, Pageable pageable);

}
