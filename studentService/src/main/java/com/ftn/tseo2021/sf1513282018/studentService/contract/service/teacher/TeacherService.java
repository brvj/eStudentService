package com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.InstitutionTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.TeacherTeachingDTO;

import org.springframework.data.domain.Pageable;

import java.util.List;

import javax.persistence.EntityNotFoundException;

public interface TeacherService extends BaseService<DefaultTeacherDTO, Integer> {

    DefaultTeacherDTO getByUserId(int userId, Pageable pageable);

    List<InstitutionTeacherDTO> filterTeachers(int institutionId, Pageable pageable, DefaultTeacherDTO filterOptions) throws PersonalizedAccessDeniedException;
    
    List<TeacherTeachingDTO> getTeacherTeachings(int teacherId, Pageable pageable) throws EntityNotFoundException;
}
