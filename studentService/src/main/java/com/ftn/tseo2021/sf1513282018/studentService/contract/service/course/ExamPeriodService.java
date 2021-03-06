package com.ftn.tseo2021.sf1513282018.studentService.contract.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.course.ExamPeriodFilterOptions;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamPeriodExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionExamPeriodDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;

public interface ExamPeriodService extends BaseService<DefaultExamPeriodDTO, Integer> {
	
    Page<InstitutionExamPeriodDTO> filterExamPeriods(int institutionId, Pageable pageable, ExamPeriodFilterOptions filterOptions);

    Page<ExamPeriodExamDTO> getExamPeriodExams(int examPeriodId, Pageable pageable) throws EntityNotFoundException;
}
