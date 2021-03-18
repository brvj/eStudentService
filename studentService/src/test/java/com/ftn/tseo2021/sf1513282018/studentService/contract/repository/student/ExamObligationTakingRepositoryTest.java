package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamObligationRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamObligationTaking;
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
class ExamObligationTakingRepositoryTest {

    @Autowired
    private ExamObligationTakingRepository examObligationTakingRepo;

    @Autowired
    private ExamObligationRepository examObligationRepo;

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Test
    final void testCreateExamObligationTaking(){
        var examObligationTaking = ExamObligationTaking.builder()
                .score(50)
                .examObligation(examObligationRepo.findById(1).get())
                .enrollment(enrollmentRepo.findById(1).get())
                .build();

        var createdExamObligationTaking = examObligationTakingRepo.save(examObligationTaking);

        assertThat(createdExamObligationTaking).isNotNull();
        assertThat(createdExamObligationTaking.getId()).isGreaterThan(0);
    }

    @Test
    final void shouldReturnExamObligationTakings_whenExamObligationIdIsPassed(){
        var examObligationTakings = examObligationTakingRepo.filterExamObligationTakings(1, null, null, null, any(Pageable.class));

        assertThat(examObligationTakings).isNotEmpty();
        assertThat(examObligationTakings.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnExamObligationTakings_whenEnrollmentIdIsPassed(){
        var examObligationTakings = examObligationTakingRepo.filterExamObligationTakings(null, 1, null, null, any(Pageable.class));

        assertThat(examObligationTakings).isNotEmpty();
        assertThat(examObligationTakings.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnExamObligationTakings_whenScoreRangeIsPassed(){
        var examObligationTakings = examObligationTakingRepo.filterExamObligationTakings(null, null, 30d, 60d, any(Pageable.class));

        assertThat(examObligationTakings).isNotEmpty();
        assertThat(examObligationTakings.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

}