package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamTaking;
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
class ExamTakingRepositoryTest {

    @Autowired
    private ExamTakingRepository examTakingRepo;

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Autowired
    private ExamRepository examRepo;

    @Test
    final void testCreateExamTaking(){
        var examTaking = ExamTaking.builder()
                .score(25d)
                .enrollment(enrollmentRepo.findById(1).get())
                .exam(examRepo.findById(1).get())
                .build();

        var createdExamTaking = examTakingRepo.save(examTaking);

        assertThat(createdExamTaking).isNotNull();
        assertThat(createdExamTaking.getId()).isGreaterThan(0);
    }

    @Test
    final void shouldReturnExamTakings_whenExamIdIsPassed(){
        var examTakings = examTakingRepo.filterExamTakings(1, null, null, null, any(Pageable.class));

        assertThat(examTakings).isNotEmpty();
        assertThat(examTakings.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnExamTakings_whenEnrollmentIdIsPassed(){
        var examTakings = examTakingRepo.filterExamTakings(null, 1, null, null, any(Pageable.class));

        assertThat(examTakings).isNotEmpty();
        assertThat(examTakings.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnExamTakings_whenScoreRangeIsPassed(){
        var examTakings = examTakingRepo.filterExamTakings(null, null, 15d, 30d, any(Pageable.class));

        assertThat(examTakings).isNotEmpty();
        assertThat(examTakings.getTotalPages()).isGreaterThanOrEqualTo(1);
    }
}