package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.UserRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.common.UserType;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.FinancialCard;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private InstitutionRepository institutionRepo;

    @Autowired
    private FinancialCardRepository financialCardRepo;

    @Test
    final void testCreateStudent(){
        var user = User.builder()
                .firstName("Student")
                .lastName("Student")
                .email("student@gmail.com")
                .username("student1")
                .password("34gfjjfn98g")
                .phoneNumber("064678342")
                .userType(UserType.STUDENT)
                .institution(institutionRepo.findById(1).get())
                .build();

        var financialCard = FinancialCard.builder()
                .currentAmmout(1000)
                .totalDeposit(8400)
                .totalSpent(8000)
                .build();

        var student = Student.builder()
                .firstName("Student")
                .lastName("Student")
                .studentCard("SF151328")
                .address("Dositejeva 67")
                .dateOfBirth(LocalDate.of(1995, 5, 21))
                .generation(2018)
                .institution(institutionRepo.findById(1).get())
                .user(user)
                .financialCard(financialCard)
                .build();

        var createdUser = userRepo.save(user);
        var createdFinancialCard = financialCardRepo.save(financialCard);
        var createdStudent = studentRepo.save(student);


        assertThat(createdFinancialCard).isNotNull();
        assertThat(createdFinancialCard.getId()).isGreaterThan(0);
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isGreaterThan(0);
        assertThat(createdStudent).isNotNull();
        assertThat(createdStudent.getId()).isGreaterThan(0);
    }

}