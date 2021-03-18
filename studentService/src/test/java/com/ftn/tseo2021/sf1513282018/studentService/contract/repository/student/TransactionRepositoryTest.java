package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import com.ftn.tseo2021.sf1513282018.studentService.model.common.TransactionType;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Transaction;
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
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private FinancialCardRepository financialCardRepo;

    @Test
    final void testCreateTransaction(){
        var transaction = Transaction.builder()
                .ammount(2000d)
                .date(LocalDate.of(2021, 01, 17))
                .transactionType(TransactionType.Payment)
                .description("Payment test desc")
                .financialCard(financialCardRepo.findById(1).get())
                .build();

        var createdTransaction = transactionRepo.save(transaction);

        assertThat(createdTransaction).isNotNull();
        assertThat(createdTransaction.getId()).isGreaterThan(0);
    }

    @Test
    final void shouldReturnTransactions_whenFinancialCardIdIsPassed(){
        var transactions = transactionRepo.filterTransaction(1, null, null, null, null, any(Pageable.class));

        assertThat(transactions).isNotEmpty();
        assertThat(transactions.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnTransactions_whenFinancialCardIdAndTRansactionTypeArePassed(){
        var transactions = transactionRepo.filterTransaction(1, TransactionType.Payment, null, null, null, any(Pageable.class));

        assertThat(transactions).isNotEmpty();
        assertThat(transactions.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnTransactions_whenFinancialCardIdAndDateRangeArePassed(){
        var transactions = transactionRepo.filterTransaction(1, null,
                LocalDate.of(2020, 10, 27), LocalDate.of(2021, 02, 01), null, any(Pageable.class));

        assertThat(transactions).isNotEmpty();
        assertThat(transactions.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

}