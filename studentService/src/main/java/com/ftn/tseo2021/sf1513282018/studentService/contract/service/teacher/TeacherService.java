package com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherFilterOptions;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.InstitutionTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.TeacherTeachingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;

public interface TeacherService extends BaseService<DefaultTeacherDTO, Integer> {

    DefaultTeacherDTO getByUserId(int userId);

    Page<InstitutionTeacherDTO> filterTeachers(int institutionId, Pageable pageable, TeacherFilterOptions filterOptions);
    
    Page<TeacherTeachingDTO> getTeacherTeachings(int teacherId, Pageable pageable);
}
