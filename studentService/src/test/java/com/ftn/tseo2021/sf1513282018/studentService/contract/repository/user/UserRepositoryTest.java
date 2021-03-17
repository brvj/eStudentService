package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.common.UserType;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private InstitutionRepository institutionRepo;

    @Test
    final void testCreateAdmin(){
        var user = User.builder()
                .firstName("Admin")
                .lastName("Admin")
                .email("admin@gmail.com")
                .username("admin1")
                .password("34gfjjfnfsjj2g")
                .phoneNumber("065789765")
                .userType(UserType.ADMIN)
                .institution(institutionRepo.findById(1).get())
                .build();

        var createdUser = userRepo.save(user);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isGreaterThan(0);
    }

    @Test
    final void testFindByUsername() {
        var user = userRepo.findByUsername("admin1").get();

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("admin1");
    }

    @Test
    final void shouldReturnUsers_whenInstitutionIdIsPassed() {
        var users = userRepo.filterUsers(1, null, null, null, null, any(Pageable.class));

        assertThat(users).isNotEmpty();
        assertThat(users.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnUsers_whenUserNameIsPassed() {
        var users = userRepo.filterUsers(1, "admin1", null, null, null, any(Pageable.class));

        assertThat(users).isNotEmpty();
        assertThat(users.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnUsers_whenUserNameAndLastNameArePassed() {
        var users = userRepo.filterUsers(1, "admin1", null, "Adm", null, any(Pageable.class));

        assertThat(users).isNotEmpty();
        assertThat(users.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnUsers_whenLastNameIsPassed() {
        var users = userRepo.filterUsers(1, null, null, "Adm", null, any(Pageable.class));

        assertThat(users).isNotEmpty();
        assertThat(users.getTotalPages()).isGreaterThanOrEqualTo(1);
    }

    @Test
    final void shouldReturnAdmins_whenUserTypeAdminIsPassed() {
        var users = userRepo.filterUsers(1, null, null, null, UserType.ADMIN, any(Pageable.class));

        assertThat(users).isNotEmpty();
        assertThat(users.getTotalPages()).isGreaterThanOrEqualTo(1);
    }
}