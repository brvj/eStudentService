package com.ftn.tseo2021.sf1513282018.studentService.contract.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExamObligationTypeService extends BaseService<DefaultExamObligationTypeDTO, Integer>{
    Page<DefaultExamObligationTypeDTO> getAll(Pageable pageable);
}
