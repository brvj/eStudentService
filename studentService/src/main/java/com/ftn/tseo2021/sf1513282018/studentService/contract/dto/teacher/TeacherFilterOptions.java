package com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class TeacherFilterOptions {

    public String firstName;
    public String lastName;
    public String address;
    public String teacherTitleName;
    public LocalDate dateOfBirthFrom;
    public LocalDate dateOfBirthTo;
    public String username;
    public String email;
    public String phoneNumber;
}
