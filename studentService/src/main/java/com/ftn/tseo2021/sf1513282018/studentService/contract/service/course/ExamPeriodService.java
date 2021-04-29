package com.ftn.tseo2021.sf1513282018.studentService.contract.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ForbiddenAccessException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamPeriodExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionExamPeriodDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

import javax.persistence.EntityNotFoundException;

public interface ExamPeriodService extends BaseService<DefaultExamPeriodDTO, Integer> {
	
    List<InstitutionExamPeriodDTO> filterExamPeriods(int institutionId, Pageable pageable, InstitutionExamPeriodDTO filterOptions) throws ForbiddenAccessException;

    List<ExamPeriodExamDTO> getExamPeriodExams(int examPeriodId, Pageable pageable) throws EntityNotFoundException;
}
