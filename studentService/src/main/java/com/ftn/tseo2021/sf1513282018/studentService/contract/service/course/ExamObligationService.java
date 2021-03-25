package com.ftn.tseo2021.sf1513282018.studentService.contract.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamObligationTakingDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

import javax.persistence.EntityNotFoundException;

public interface ExamObligationService extends BaseService<DefaultExamObligationDTO, Integer>{
    DefaultExamObligationDTO getByExamObligationTaking(DefaultExamObligationTakingDTO t);

    List<DefaultExamObligationDTO> filterExamObligations(DefaultExamObligationDTO filterOptions, Pageable pageable);
    
    List<DefaultExamObligationDTO> getByCourseId(int courseId, Pageable pageable);
    
    List<DefaultExamObligationTakingDTO> getExamObligationTakings(int examObligationId, Pageable pageable) throws EntityNotFoundException;
}
