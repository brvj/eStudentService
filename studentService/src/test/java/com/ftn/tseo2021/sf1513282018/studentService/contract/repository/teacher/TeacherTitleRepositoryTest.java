package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherTitle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherTitleRepositoryTest {

    @Autowired
    private TeacherTitleRepository teacherTitleRepo;

    @Test
    final void testCreateTeacherTitle(){
        var teacherTitle = TeacherTitle.builder()
                .name("associate professor")
                .build();

        var createdTeacherTitle = teacherTitleRepo.save(teacherTitle);

        assertThat(createdTeacherTitle).isNotNull();
        assertThat(createdTeacherTitle.getId()).isGreaterThan(0);
        assertThat(createdTeacherTitle.getName()).isEqualTo("associate professor");
    }

}