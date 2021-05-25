package com.ftn.tseo2021.sf1513282018.studentService.contract.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamOblExamObligationTakingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import javax.persistence.EntityNotFoundException;

public interface ExamObligationService extends BaseService<DefaultExamObligationDTO, Integer> {

    Page<CourseExamObligationDTO> filterExamObligations(int courseId, Pageable pageable, CourseExamObligationDTO filterOptions) throws PersonalizedAccessDeniedException;
    
    Page<ExamOblExamObligationTakingDTO> getExamObligationTakings(int examObligationId, Pageable pageable) throws EntityNotFoundException;
}
