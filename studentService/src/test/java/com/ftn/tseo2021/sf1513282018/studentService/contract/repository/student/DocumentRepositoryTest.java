package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Test
    final void testCreateDocument(){
        var document = Document.builder()
                .name("test doc")
                .path("/home/tijana/docs")
                .type("doc/docx")
                .student(studentRepo.findById(1).get())
                .build();

        var createdDocument = documentRepo.save(document);

        assertThat(createdDocument).isNotNull();
        assertThat(createdDocument.getId()).isGreaterThan(0);
    }

    @Test
    final void shouldReturnDocuments_whenStudentIdIsPassed(){
        var documents = documentRepo.filterDocuments(1, null, any(Pageable.class));

        assertThat(documents).isNotEmpty();
        assertThat(documents.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnDocuments_whenStudentIdAndDocNameArePassed(){
        var documents = documentRepo.filterDocuments(1, "doc", any(Pageable.class));

        assertThat(documents).isNotEmpty();
        assertThat(documents.getTotalPages()).isGreaterThanOrEqualTo(1);
    }


}