package com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.TeacherTeachingDTO;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeachingService extends BaseService<DefaultTeachingDTO, Integer> {

    List<TeacherTeachingDTO> filterTeachingsByTeacher(int teacherId, Pageable pageable, TeacherTeachingDTO filterOptions);

    List<DefaultTeachingDTO> getByCourseId(int courseId, Pageable pageable);
}
