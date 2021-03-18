package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.ExamTakingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExamRepositoryTest {

    @Autowired
    private ExamPeriodRepository examPeriodRepo;

    @Autowired
    private ExamRepository examRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private ExamTakingRepository examTakingRepo;

    @Test
    final void testCreateExam(){
        var exam = Exam.builder()
                .dateTime(LocalDateTime.now())
                .classroom("212a")
                .examPeriod(examPeriodRepo.findById(1).get())
                .course(courseRepo.findById(1).get())
                .build();

        var createdExam = examRepo.save(exam);

        assertThat(createdExam).isNotNull();
        assertThat(createdExam.getId()).isGreaterThan(0);
    }

    @Test
    final void shouldReturnExam_whenExamTakingIsPassed(){
        var exam = examRepo.findByExamTakingsContaining(examTakingRepo.findById(1).get());

        assertThat(exam).isNotNull();
        assertThat(exam.getClassroom()).isEqualTo("212a");
    }

    @Test
    final void shouldReturnExams_whenCourseIdAndExamPeriodIdArePassed(){
        var exams = examRepo.filterExams(1, 1, null, null, null, any(Pageable.class));

        assertThat(exams).isNotEmpty();
        assertThat(exams.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnExams_whenCourseIdAndExamPeriodIdAndDateRangeArePassed(){
        var exams = examRepo.filterExams(1, 1, null,
                LocalDateTime.of(2021, 02, 20, 12, 30),
                LocalDateTime.of(2021, 03, 28, 13, 00), any(Pageable.class));

        assertThat(exams).isNotEmpty();
        assertThat(exams.getTotalPages()).isGreaterThanOrEqualTo(1);
    }
}