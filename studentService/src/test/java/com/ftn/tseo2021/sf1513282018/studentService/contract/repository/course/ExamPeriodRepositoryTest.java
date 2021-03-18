package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamPeriod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExamPeriodRepositoryTest {

    @Autowired
    private ExamPeriodRepository examPeriodRepo;

    @Autowired
    private InstitutionRepository institutionRepo;

    @Autowired
    private ExamRepository examRepo;

    @Test
    final void testCreateExamPeriod(){
        var examPeriod = ExamPeriod.builder()
                .name("January Exam Period")
                .startDate(LocalDate.of(2021, 1, 21))
                .endDate(LocalDate.of(2021, 2, 5))
                .institution(institutionRepo.findById(1).get())
                .build();

        var createdExamPeriod = examPeriodRepo.save(examPeriod);

        assertThat(createdExamPeriod).isNotNull();
        assertThat(createdExamPeriod.getId()).isGreaterThan(0);
    }

    @Test
    final void shouldReturnExamPeriods_whenInstitutionIsPassed(){
        var examPeriods = examPeriodRepo.findAllByInstitution(institutionRepo.findById(1).get(), any(Pageable.class));

        assertThat(examPeriods).isNotEmpty();
        assertThat(examPeriods.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnExamPeriod_whenExamIsPassed(){
        var examPeriod = examPeriodRepo.findByExamsContaining(examRepo.findById(1).get());

        assertThat(examPeriod).isNotNull();
        assertThat(examPeriod.getName()).isEqualTo("January Exam Period");
    }

    @Test
    final void shouldReturnExamPeriods_whenInstitutionIdAndNameArePassed(){
        var examPeriods = examPeriodRepo.filterExamPeriods(1, "january", null, null, any(Pageable.class));

        assertThat(examPeriods).isNotNull();
        assertThat(examPeriods.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnExamPeriods_whenInstitutionIdAndDateRangeArePassed(){
        var examPeriods = examPeriodRepo.filterExamPeriods(1, null, LocalDate.of(2021, 01, 10),
                LocalDate.of(2021, 02, 20), any(Pageable.class));

        assertThat(examPeriods).isNotNull();
        assertThat(examPeriods.getTotalPages()).isGreaterThanOrEqualTo(1);
    }
}