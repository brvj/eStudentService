package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.ExamObligationTakingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;
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
class ExamObligationRepositoryTest {

    @Autowired
    private ExamObligationRepository examObligationRepository;

    @Autowired
    private ExamObligationTypeRepository examObligationTypeRepo;

    @Autowired
    private ExamObligationTakingRepository examObligationTakingRepo;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    final void testCreateExamObligation(){
        var examObligation = ExamObligation.builder()
                .points(70)
                .description("Exam obligation desc")
                .examObligationType(examObligationTypeRepo.findById(1).get())
                .course(courseRepository.findById(1).get())
                .build();

        var createdExamObligation = examObligationRepository.save(examObligation);

        assertThat(createdExamObligation).isNotNull();
        assertThat(createdExamObligation.getId()).isGreaterThan(0);
    }

    @Test
    final void shouldReturnExamObligation_whenExamObligationTakingIsPassed(){
        var examObligation = examObligationRepository.findByExamObligationTaking(examObligationTakingRepo.findById(1).get());

        assertThat(examObligation).isNotNull();
    }

    @Test
    final void shouldReturnExamObligations_whenCourseIdIsPassed(){
        var examObligations = examObligationRepository.filterExamObligations(1, null, null, any(Pageable.class));

        assertThat(examObligations).isNotEmpty();
        assertThat(examObligations.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnExamObligations_whenCourseIdAndObligationTypeArePassed(){
        var examObligations = examObligationRepository.filterExamObligations(1, null, 1, any(Pageable.class));

        assertThat(examObligations).isNotEmpty();
        assertThat(examObligations.getTotalPages()).isGreaterThanOrEqualTo(1);
    }
}