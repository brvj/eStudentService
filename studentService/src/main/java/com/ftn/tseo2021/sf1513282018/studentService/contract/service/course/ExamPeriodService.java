package com.ftn.tseo2021.sf1513282018.studentService.contract.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionExamPeriodDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

import javax.persistence.EntityNotFoundException;

public interface ExamPeriodService extends BaseService<DefaultExamPeriodDTO, Integer> {
	
    List<InstitutionExamPeriodDTO> getByInstitutionId(int institutionId, Pageable pageable);

    DefaultExamPeriodDTO getByExam(DefaultExamDTO t);

    List<DefaultExamPeriodDTO> filterExamPeriods(DefaultExamPeriodDTO t, Pageable pageable);
    
    List<DefaultExamDTO> getExamPeriodExams(int examPeriodId, Pageable pageable) throws EntityNotFoundException;
}
