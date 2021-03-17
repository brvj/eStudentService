package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.CourseRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;
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
class TeachingRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private TeacherRoleRepository teacherRoleRepo;

    @Autowired
    private TeachingRepository teachingRepo;

    @Test
    final void testCreateTeaching(){
        var teaching = Teaching.builder()
                .startDate(LocalDate.of(2020, 02, 20))
                .teacherRole(teacherRoleRepo.findById(1).get())
                .teacher(teacherRepo.findById(1).get())
                .course(courseRepo.findById(1).get())
                .build();

        var createdTeaching = teachingRepo.save(teaching);

        assertThat(createdTeaching).isNotNull();
        assertThat(createdTeaching.getId()).isGreaterThan(0);
    }

    @Test
    final void shouldReturnTeachings_whenTeacherIdIsPassed(){
        var teachings = teachingRepo.filterTeachings(1, null, null, any(Pageable.class));

        assertThat(teachings).isNotEmpty();
        assertThat(teachings.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnTeachings_whenCourseIdIsPassed(){
        var teachings = teachingRepo.filterTeachings(null, 1, null, any(Pageable.class));

        assertThat(teachings).isNotEmpty();
        assertThat(teachings.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnTeachings_whenTeacherRoleIdIsPassed(){
        var teachings = teachingRepo.filterTeachings(null, null, 1, any(Pageable.class));

        assertThat(teachings).isNotEmpty();
        assertThat(teachings.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

}