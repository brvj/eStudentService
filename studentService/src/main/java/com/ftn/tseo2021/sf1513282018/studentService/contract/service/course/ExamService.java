package com.ftn.tseo2021.sf1513282018.studentService.contract.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamTakingDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExamService extends BaseService<DefaultExamDTO, Integer>{
    DefaultExamDTO getByExamTaking(DefaultExamTakingDTO t);

    List<DefaultExamDTO> filterExams(DefaultExamDTO filterOptions, Pageable pageable);
}
