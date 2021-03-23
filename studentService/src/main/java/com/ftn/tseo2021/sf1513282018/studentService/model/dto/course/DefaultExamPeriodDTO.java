package com.ftn.tseo2021.sf1513282018.studentService.model.dto.course;

import java.time.LocalDate;
import java.util.Set;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultExamPeriodDTO implements ExamPeriodDTO{
    public int id;
    public String name;
    public LocalDate startDate;
    public LocalDate endDate;
    public DefaultInstitutionDTO institution;
}
