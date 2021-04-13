package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.CourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.CourseRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.CourseService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamObligationService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.EnrollmentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeachingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultCourseService implements CourseService {
	
	@Autowired
	private CourseRepository courseRepo;

	@Autowired
	private DtoConverter<Course, CourseDTO, DefaultCourseDTO> courseConverter;
	
	@Autowired
	private TeachingService teachingService;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private ExamService examService;
	
	@Autowired
	private ExamObligationService examObligationService;

	@Override
	public boolean existsById(Integer id) {
		return courseRepo.existsById(id);
	}

	@Override
	public DefaultCourseDTO getOne(Integer id) {
		Optional<Course> course = courseRepo.findById(id);
		return courseConverter.convertToDTO(course.orElse(null));
	}

	@Override
	public Integer create(DefaultCourseDTO dto) throws IllegalArgumentException {
		Course course = courseConverter.convertToJPA(dto);

		course = courseRepo.save(course);

		return course.getId();
	}

	@Override
	public void update(Integer id, DefaultCourseDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if(!courseRepo.existsById(id)) throw new EntityNotFoundException();

		Course cNew = courseConverter.convertToJPA(dto);
		
		Course c = courseRepo.findById(id).get();
		c.setName(cNew.getName());
		courseRepo.save(c);
	}

	@Override
	public boolean delete(Integer id) {
		if(!courseRepo.existsById(id)) return false;

		courseRepo.deleteById(id);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InstitutionCourseDTO> filterCourses(int institutionId, Pageable pageable, InstitutionCourseDTO filterOptions) {
		if (filterOptions == null) {
			Page<Course> page = courseRepo.findByInstitution_Id(institutionId, pageable);
			return (List<InstitutionCourseDTO>) courseConverter.convertToDTO(page.getContent(), InstitutionCourseDTO.class);
		}
		else {
			Page<Course> page = courseRepo.filterCourses(institutionId, filterOptions.name, pageable);
			return (List<InstitutionCourseDTO>) courseConverter.convertToDTO(page.getContent(), InstitutionCourseDTO.class);
		}
	}

	@Override
	public List<CourseTeachingDTO> getCourseTeachings(int courseId, Pageable pageable) throws EntityNotFoundException {
		if (!courseRepo.existsById(courseId)) throw new EntityNotFoundException();
		
		return teachingService.filterTeachingsByCourse(courseId, pageable, null);
	}

	@Override
	public List<CourseEnrollmentDTO> getCourseEnrollments(int courseId, Pageable pageable)
			throws EntityNotFoundException {
		if (!courseRepo.existsById(courseId)) throw new EntityNotFoundException();
		
		return enrollmentService.filterEnrollmentsByCourse(courseId, pageable, null);
	}

	@Override
	public List<CourseExamObligationDTO> getCourseExamObligations(int courseId, Pageable pageable)
			throws EntityNotFoundException {
		if (!courseRepo.existsById(courseId)) throw new EntityNotFoundException();
		
		return examObligationService.filterExamObligations(courseId, pageable, null);
	}

	@Override
	public List<CourseExamDTO> getCourseExams(int courseId, Pageable pageable) throws EntityNotFoundException {
		if (!courseRepo.existsById(courseId)) throw new EntityNotFoundException();
		
		return examService.filterExamsByCourse(courseId, pageable, null);
	}
}
