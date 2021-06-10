package com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherRoleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherRoleService extends BaseService<DefaultTeacherRoleDTO, Integer> {
    Page<DefaultTeacherRoleDTO> getAll(Pageable pageable);
}
