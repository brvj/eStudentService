package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.CourseService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeTeacher;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeachingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeachingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.TeacherTeachingDTO;

import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeTeacherOrAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultTeachingService implements TeachingService {

	@Autowired
	private TeachingRepository teachingRepo;

	@Autowired
	private DtoConverter<Teaching, TeachingDTO, DefaultTeachingDTO> teachingConverter;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private PersonalizedAuthorizator authorizator;

	private CustomPrincipal getPrincipal() { return authorizator.getPrincipal(); }

	@Override
	public DefaultTeachingDTO getOne(Integer id) {
		Teaching teaching = teachingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());

		return teachingConverter.convertToDTO(teaching);
	}

	@AuthorizeAdmin
	@Override
	public Integer create(DefaultTeachingDTO dto){
		try{
			DefaultTeacherDTO teacher = teacherService.getOne(dto.getTeacher().getId());
			DefaultCourseDTO course = courseService.getOne(dto.getCourse().getId());

			if(teacher.getInstitution().getId() != course.getInstitution().getId()) throw new EntityValidationException();

			authorizator.assertPrincipalIsFromInstitution(teacher.getInstitution().getId(), EntityValidationException.class);

		}catch(ResourceNotFoundException | NullPointerException e){ throw new EntityValidationException();}

		Teaching teaching = teachingConverter.convertToJPA(dto);

		teaching = teachingRepo.save(teaching);

		return teaching.getId();
	}

	@AuthorizeAdmin
	@Override
	public void update(Integer id, DefaultTeachingDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		Teaching teaching = teachingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());

		authorizator.assertPrincipalIsFromInstitution(teaching.getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);

		try{
			DefaultTeacherDTO teacher = teacherService.getOne(dto.getTeacher().getId());
			DefaultCourseDTO course = courseService.getOne(dto.getCourse().getId());

			if(teacher.getInstitution().getId() != course.getInstitution().getId() || teaching.getCourse().getId() != dto.getCourse().getId()
			 || teaching.getTeacher().getId() != dto.getTeacher().getId()){throw new EntityValidationException();}

		}catch(NullPointerException | ResourceNotFoundException e){ throw new EntityValidationException();}

		Teaching tNew = teachingConverter.convertToJPA(dto);

		teaching.setTeacherRole(tNew.getTeacherRole());
		teachingRepo.save(teaching);
	}

	@AuthorizeAdmin
	@Override
	public void delete(Integer id) {
		Teaching t = teachingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());

		authorizator.assertPrincipalIsFromInstitution(t.getCourse().getInstitution().getId(), PersonalizedAccessDeniedException.class);

		teachingRepo.deleteById(id);
	}

	@AuthorizeTeacherOrAdmin
	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherTeachingDTO> filterTeachingsByTeacher(int teacherId, Pageable pageable, TeacherTeachingDTO filterOptions) {
		if(getPrincipal().isTeacher()){
			authorizator.assertPrincipalTeacherIdIs(teacherId, PersonalizedAccessDeniedException.class);
		}else if(getPrincipal().isAdmin()){
			DefaultTeacherDTO teacher = teacherService.getOne(teacherId);
			authorizator.assertPrincipalIsFromInstitution(teacher.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		}

		Page<Teaching> page;
		if(filterOptions == null) {
			page = teachingRepo.findByTeacher_Id(teacherId, pageable);
		}
		else {
			page = teachingRepo.filterTeachingsByTeacher(teacherId, filterOptions.getTeacherRole().getId(), pageable);
		}
		return (List<TeacherTeachingDTO>) teachingConverter.convertToDTO(page.getContent(), TeacherTeachingDTO.class);
	}

	@AuthorizeAny
	@SuppressWarnings("unchecked")
	@Override
	public List<CourseTeachingDTO> filterTeachingsByCourse(int courseId, Pageable pageable, CourseTeachingDTO filterOptions) {
		if(getPrincipal().isTeacher()){
			authorizator.assertTeacherIsTeachingCourse(courseId, PersonalizedAccessDeniedException.class);
		}else if(getPrincipal().isStudent()){
			authorizator.assertStudentIsEnrollingCourse(courseId, PersonalizedAccessDeniedException.class);
		}else if(getPrincipal().isAdmin()){
			DefaultCourseDTO c = courseService.getOne(courseId);
			authorizator.assertPrincipalIsFromInstitution(c.getInstitution().getId(), PersonalizedAccessDeniedException.class);
		}

		Page<Teaching> page;
		if(filterOptions == null) {
			page = teachingRepo.findByCourse_Id(courseId, pageable);
		}
		else {
			page = teachingRepo.filterTeachingsByCourse(courseId, filterOptions.getTeacherRole().getId(), pageable);
		}
		return (List<CourseTeachingDTO>) teachingConverter.convertToDTO(page.getContent(), CourseTeachingDTO.class);
	}
}
