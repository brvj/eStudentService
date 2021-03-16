package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

    //Optional<Student> findByUser_Id(int userId);
	
    //filter(int institutionId, String firstName, String lastName, 
	//String indexNum, String address, LocalDate startBirthDate, LocalDate endBirthDate, 
	//Integer startGeneration, Integer endGeneration, Pageable pageable)


}
