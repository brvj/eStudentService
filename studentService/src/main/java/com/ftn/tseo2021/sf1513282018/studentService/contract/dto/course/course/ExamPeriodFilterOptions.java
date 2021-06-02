package com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamPeriodFilterOptions {

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;
}
