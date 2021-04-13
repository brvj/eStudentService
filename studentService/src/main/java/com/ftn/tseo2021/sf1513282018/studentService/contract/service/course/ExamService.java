package com.ftn.tseo2021.sf1513282018.studentService.contract.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamTakingDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

import javax.persistence.EntityNotFoundException;

public interface ExamService extends BaseService<DefaultExamDTO, Integer> {

    List<DefaultExamDTO> getByExamPeriodId(int examPeriodId, Pageable pageable);
    
    List<CourseExamDTO> filterExamsByCourse(int courseId, Pageable pageable, CourseExamDTO filterOptions);
    
    List<DefaultExamTakingDTO> getExamExamTakings(int examId, Pageable pageable) throws EntityNotFoundException;
}
