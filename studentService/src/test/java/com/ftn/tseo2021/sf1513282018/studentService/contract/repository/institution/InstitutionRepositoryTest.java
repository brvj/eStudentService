package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InstitutionRepositoryTest {

    @Autowired
    private InstitutionRepository institutionRepo;

    @Test
    final void testCreateInstitution(){
        var institution = Institution.builder()
                .name("Faculty of Technical Sciences")
                .address("Trg Dositeja Obradovica 3")
                .phoneNumber("021345678")
                .build();

        var createdInstitution = institutionRepo.save(institution);

        assertThat(createdInstitution).isNotNull();
        assertThat(createdInstitution.getId()).isGreaterThan(0);
    }
}