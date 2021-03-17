package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.FinancialCard;
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
class FinancialCardRepositoryTest {

    @Autowired
    private FinancialCardRepository financialCardRepo;

    @Autowired
    private StudentRepository studentRepo;

}