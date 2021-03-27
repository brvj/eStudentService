package com.ftn.tseo2021.sf1513282018.studentService.service.institution;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.CourseService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamPeriodService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserService;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class DefaultInstitutionService implements InstitutionService {

	private InstitutionRepository institutionRepo;

	private UserService userService;

	private  StudentService studentService;

	private TeacherService teacherService;

	private CourseService courseService;

	private ExamPeriodService examPeriodService;

	private DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> institutionConverter;

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

		List<DefaultUserDTO> users = userService.getByInstitutionId(institutionId, pageable);

		return users;
	}

	@Override
	public List<DefaultTeacherDTO> getInstitutionTeachers(int institutionId, Pageable pageable)
			throws EntityNotFoundException {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		List<DefaultTeacherDTO> teachers = teacherService.getByInstitutionId(institutionId, pageable);

		return teachers;
	}

	@Override
	public List<DefaultStudentDTO> getInstitutionStudents(int institutionId, Pageable pageable)
			throws EntityNotFoundException {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		List<DefaultStudentDTO> students = studentService.getByInstitutionId(institutionId, pageable);

		return students;
	}

	@Override
	public List<DefaultCourseDTO> getInstitutionCourses(int institutionId, Pageable pageable)
			throws EntityNotFoundException {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		List<DefaultCourseDTO> courses = courseService.getByInstitutionId(institutionId, pageable);

		return courses;
	}

	@Override
	public List<DefaultExamPeriodDTO> getInstitutionExamPeriods(int institutionId, Pageable pageable)
			throws EntityNotFoundException {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		List<DefaultExamPeriodDTO> examPeriods = examPeriodService.getByInstitutionId(institutionId, pageable);

		return examPeriods;
	}
}
