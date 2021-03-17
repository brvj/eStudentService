package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.UserRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.common.UserType;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
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
class TeacherRepositoryTest {

    @Autowired
    private InstitutionRepository institutionRepo;

    @Autowired
    private TeacherRepository teacherRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TeacherTitleRepository teacherTitleRepo;

    @Test
    final void testCreateTeacher(){
        var user = User.builder()
                .firstName("Teacher")
                .lastName("Teacher")
                .email("teacher@gmail.com")
                .username("teacher1")
                .password("34gfjjfnfsjj2g")
                .phoneNumber("065789765")
                .userType(UserType.TEACHER)
                .institution(institutionRepo.findById(1).get())
                .build();

        var teacher = Teacher.builder()
                .firstName("Teacher")
                .lastName("Teacher")
                .address("Puskinova 23")
                .dateOfBirth(LocalDate.of(1987, 12, 10))
                .institution(institutionRepo.findById(1).get())
                .user(user)
                .teacherTitle(teacherTitleRepo.findById(1).get())
                .build();

        var createdUser = userRepo.save(user);
        var createdTeacher = teacherRepo.save(teacher);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isGreaterThan(0);

        assertThat(createdTeacher).isNotNull();
        assertThat(createdTeacher.getId()).isGreaterThan(0);
    }

    @Test
    final void testFindByUserId(){
        var teacher = teacherRepo.findByUser_Id(2);

        assertThat(teacher).isNotNull();
        assertThat(teacher.get().getFirstName()).isEqualTo("Teacher");
    }

    @Test
    final void shouldReturnTeachers_whenInstitutionIdIsPassed(){
        var teachers = teacherRepo.filterTeachers(1, null, null, null, any(Pageable.class));

        assertThat(teachers).isNotEmpty();
        assertThat(teachers.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnTeachers_whenInstitutionIdAndTeacherTitleIdArePassed(){
        var teachers = teacherRepo.filterTeachers(1, 1, null, null, any(Pageable.class));

        assertThat(teachers).isNotEmpty();
        assertThat(teachers.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnTeachers_whenInstitutionIdAndFirstNameArePassed(){
        var teachers = teacherRepo.filterTeachers(1, null, "Teacher", null, any(Pageable.class));

        assertThat(teachers).isNotEmpty();
        assertThat(teachers.getTotalPages()).isGreaterThanOrEqualTo(1);
    }
}