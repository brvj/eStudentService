package com.ftn.tseo2021.sf1513282018.studentService.service.institution;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.CourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.UserDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.CourseRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamPeriodRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.StudentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.user.UserRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamPeriod;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultInstitutionService implements InstitutionService {
	
	@Autowired
	private InstitutionRepository institutionRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TeacherRepository teacherRepo;

	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private CourseRepository courseRepo;

	@Autowired
	private ExamPeriodRepository examPeriodRepo;

	@Autowired
	private DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> institutionConverter;

	@Autowired
	private DtoConverter<User, UserDTO, DefaultUserDTO> userConverter;

	@Autowired
	private DtoConverter<Teacher, TeacherDTO, DefaultTeacherDTO> teacherConverter;

	@Autowired
	private DtoConverter<Student, StudentDTO, DefaultStudentDTO> studentConverter;

	@Autowired
	private DtoConverter<Course, CourseDTO, DefaultCourseDTO> courseConverter;

	@Autowired
	private DtoConverter<ExamPeriod, ExamPeriodDTO, DefaultExamPeriodDTO> examPeriodConverter;

	@Override
	public DefaultInstitutionDTO getOne(Integer id) {
		Optional<Institution> institution = institutionRepo.findById(id);
		return institutionConverter.convertToDTO(institution.orElse(null));
	}

	@Override
	public Integer create(DefaultInstitutionDTO t) {
		Institution institution = institutionConverter.convertToJPA(t);

		institution = institutionRepo.save(institution);

		return institution.getId();
	}

	@Override
	public void update(Integer id, DefaultInstitutionDTO t) {
		if(!institutionRepo.existsById(id)) throw new EntityNotFoundException();

		t.setId(id);
		Institution institution = institutionConverter.convertToJPA(t);

		institutionRepo.save(institution);
	}

	@Override
	public boolean delete(Integer id) {
		if(!institutionRepo.existsById(id)) return false;

		institutionRepo.deleteById(id);
		return true;
	}

	@Override
	public List<DefaultUserDTO> getInstitutionUsers(int institutionId, Pageable pageable)
			throws EntityNotFoundException {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		Page<User> page = userRepo.filterUsers(institutionId, null, null, null, pageable);

		return userConverter.convertToDTO(page.getContent());
	}

	@Override
	public List<DefaultTeacherDTO> getInstitutionTeachers(int institutionId, Pageable pageable)
			throws EntityNotFoundException {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		Page<Teacher> page = teacherRepo.filterTeachers(institutionId, null, null, null, pageable);

		return teacherConverter.convertToDTO(page.getContent());
	}

	@Override
	public List<DefaultStudentDTO> getInstitutionStudents(int institutionId, Pageable pageable)
			throws EntityNotFoundException {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		Page<Student> page = studentRepo.filterStudent(institutionId, null, null, null, null,
				null, null, null, null, pageable);

		return studentConverter.convertToDTO(page.getContent());
	}

	@Override
	public List<DefaultCourseDTO> getInstitutionCourses(int institutionId, Pageable pageable)
			throws EntityNotFoundException {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		Page<Course> page = courseRepo.filterCourses(institutionId, null, pageable);

		return courseConverter.convertToDTO(page.getContent());
	}

	@Override
	public List<DefaultExamPeriodDTO> getInstitutionExamPeriods(int institutionId, Pageable pageable)
			throws EntityNotFoundException {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		Page<ExamPeriod> page = examPeriodRepo.filterExamPeriods(institutionId, null, null, null, pageable);

		return examPeriodConverter.convertToDTO(page.getContent());
	}
}
