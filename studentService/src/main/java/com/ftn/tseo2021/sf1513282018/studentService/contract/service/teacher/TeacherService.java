package com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService extends BaseService<DefaultTeacherDTO, Integer> {

    DefaultTeacherDTO getByUserId(int userId, Pageable pageable);

    List<DefaultTeacherDTO> getByInstitutionId(int institutionId, Pageable pageable);
}
