package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeachingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private InstitutionRepository institutionRepo;

    @Autowired
    private TeachingRepository teachingRepo;

    @Test
    final void testCreateCourse(){
        var course = Course.builder()
                .name("eEducation")
                .institution(institutionRepo.findById(1).get())
                .build();

        var createdCourse = courseRepo.save(course);

        assertThat(createdCourse).isNotNull();
        assertThat(createdCourse.getName()).isEqualTo("eEducation");
    }

    @Test
    final void shouldReturnCourse_whenTeachingIsPassed(){
        var course = courseRepo.findByTeachingsContaining(teachingRepo.findById(1).get());

        assertThat(course).isNotNull();
        assertThat(course.getName()).isEqualTo("eEducation");
    }
}