package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    Optional<Teacher> findByUser_Id(int userId);
    
    Page<Teacher> findByInstitution_Id(int institutionId, Pageable pageable);

    @Query("SELECT t from Teacher t WHERE " +
            "t.institution.id = :institutionId AND " +
            "(:firstName is null OR lower(t.firstName) LIKE lower(CONCAT('%', :firstName,'%'))) AND " +
            "(:lastName is null OR lower(t.lastName) LIKE lower(CONCAT('%', :lastName,'%'))) AND " +
            "(:address is null OR lower(t.address) LIKE lower(CONCAT('%', :address, '%'))) AND " +
            "(:teacherTitleName is null OR lower(t.teacherTitle.name) LIKE lower(CONCAT('%', :teacherTitleName, '%'))) AND " +
            "(:startBirthDate is null OR t.dateOfBirth >= :startBirthDate) AND " +
            "(:endBirthDate is null OR t.dateOfBirth <= :endBirthDate) AND " +
            "(:username is null OR lower(t.user.username) LIKE lower(CONCAT('%', :username, '%'))) AND " +
            "(:email is null OR lower(t.user.email) LIKE lower(CONCAT('%', :email, '%'))) AND " +
            "(:phoneNumber is null OR lower(t.user.phoneNumber) LIKE lower(CONCAT('%', :phoneNumber, '%')))")
    Page<Teacher> filterTeachers(@Param("institutionId") int institutionId,
                                 @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("address") String address,
                                 @Param("teacherTitleName") String teacherTitleName, @Param("startBirthDate") LocalDate startBirthDate,
                                 @Param("endBirthDate") LocalDate endBirthDate, @Param("username") String username, @Param("email") String email,
                                 @Param("phoneNumber") String phoneNumber, Pageable pageable);
}
