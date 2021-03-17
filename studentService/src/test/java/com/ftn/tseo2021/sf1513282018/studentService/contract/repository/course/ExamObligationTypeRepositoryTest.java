package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExamObligationTypeRepositoryTest {

    @Autowired
    private ExamObligationTypeRepository examObligationTypeRepo;

    @Test
    final void testCreateExamObligationType(){
        var examObligationType = ExamObligationType.builder()
                .name("colloquium")
                .build();

        var createdExamObligationType = examObligationTypeRepo.save(examObligationType);

        assertThat(createdExamObligationType).isNotNull();
        assertThat(createdExamObligationType.getId()).isGreaterThan(0);
    }

}