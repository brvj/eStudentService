package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherRoleRepositoryTest {

    @Autowired
    private TeacherRoleRepository teacherRoleRepo;

    @Test
    final void testCreateTeacherRole(){
        var teacherRole = TeacherRole.builder()
                .name("Lecturer")
                .build();

        var createdTeacherRole = teacherRoleRepo.save(teacherRole);

        assertThat(createdTeacherRole).isNotNull();
        assertThat(createdTeacherRole.getName()).isEqualTo("Lecturer");
    }
}