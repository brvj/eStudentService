package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.CourseRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;
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
class EnrollmentRepositoryTest {

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Test
    final void testCreateEnrollment(){
        var enrollment = Enrollment.builder()
                .startDate(LocalDate.of(2020, 10, 1))
                .grade(9)
                .score(82)
                .passed(true)
                .student(studentRepo.findById(1).get())
                .course(courseRepo.findById(1).get())
                .build();

        var createdEnrollment = enrollmentRepo.save(enrollment);

        assertThat(createdEnrollment).isNotNull();
        assertThat(createdEnrollment.getId()).isGreaterThan(0);
    }

    @Test
    final void shouldReturnEnrollments_whenStudentIdIsPassed(){
        var enrollments = enrollmentRepo.filterEnrollments(1, null, null,
                null, null, null, null, null, null, any(Pageable.class));

        assertThat(enrollments).isNotEmpty();
        assertThat(enrollments.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnEnrollments_whenCourseIdIsPassed(){
        var enrollments = enrollmentRepo.filterEnrollments(null, 1, null,
                null, null, null, null, null, null, any(Pageable.class));

        assertThat(enrollments).isNotEmpty();
        assertThat(enrollments.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnEnrollments_whenStartDateScopeIsPassed(){
        var enrollments = enrollmentRepo.filterEnrollments(null, null, LocalDate.of(2020, 9, 25),
                LocalDate.of(2020, 10, 25), null, null, null, null, null, any(Pageable.class));

        assertThat(enrollments).isNotEmpty();
        assertThat(enrollments.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnEnrollments_whenStudentIdAndPassArePassed(){
        var enrollments = enrollmentRepo.filterEnrollments(1, null, null,
                null, true, null, null, null, null, any(Pageable.class));

        assertThat(enrollments).isNotEmpty();
        assertThat(enrollments.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnEnrollments_whenStudentIdAndScoreRangeIsPassed(){
        var enrollments = enrollmentRepo.filterEnrollments(1, null, null,
                null, null, 81d, 90d, null, null, any(Pageable.class));

        assertThat(enrollments).isNotEmpty();
        assertThat(enrollments.getTotalPages()).isGreaterThanOrEqualTo(1);
    }
}