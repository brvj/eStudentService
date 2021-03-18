package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

	@Query("SELECT doc from Document doc WHERE " +
			"doc.student.id = :studentId AND " +
			"(:name is null OR lower(doc.name) LIKE lower(CONCAT('%', :name, '%')))")
	Page<Document> filterDocuments(@Param("studentId") int studentId, @Param("name") String name, Pageable pageable);

}
