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
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.InstitutionStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.InstitutionTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.InstitutionUserDTO;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultInstitutionService implements InstitutionService {

	private InstitutionRepository institutionRepo;

	private UserService userService;

	private StudentService studentService;

	private TeacherService teacherService;

	private CourseService courseService;

	private ExamPeriodService examPeriodService;

	private DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> institutionConverter;

	@Override
	public boolean existsById(Integer id) {
		return institutionRepo.existsById(id);
	}
	
	@Override
	public DefaultInstitutionDTO getOne(Integer id) {
		Optional<Institution> institution = institutionRepo.findById(id);
		return institutionConverter.convertToDTO(institution.orElse(null));
	}

	@Override
	public Integer create(DefaultInstitutionDTO dto) throws IllegalArgumentException {
		Institution institution = institutionConverter.convertToJPA(dto);

		institution = institutionRepo.save(institution);

		return institution.getId();
	}

	@Override
	public void update(Integer id, DefaultInstitutionDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if(!institutionRepo.existsById(id)) throw new EntityNotFoundException();
		
		Institution iNew = institutionConverter.convertToJPA(dto);
		
		Institution i = institutionRepo.findById(id).get();
		i.setName(iNew.getName());
		i.setAddress(iNew.getAddress());
		i.setPhoneNumber(iNew.getPhoneNumber());
		institutionRepo.save(i);

	}

	@Override
	public boolean delete(Integer id) {
		if(!institutionRepo.existsById(id)) return false;

		institutionRepo.deleteById(id);
		return true;
	}

	@Override
	public List<InstitutionUserDTO> getInstitutionUsers(int institutionId, Pageable pageable)
			throws EntityNotFoundException {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		List<InstitutionUserDTO> users = userService.filterUsers(institutionId, pageable, null);

		return users;
	}

	@Override
	public List<InstitutionTeacherDTO> getInstitutionTeachers(int institutionId, Pageable pageable)
			throws EntityNotFoundException {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		List<InstitutionTeacherDTO> teachers = teacherService.filterTeachers(institutionId, pageable, null);

		return teachers;
	}

	@Override
	public List<InstitutionStudentDTO> getInstitutionStudents(int institutionId, Pageable pageable)
			throws EntityNotFoundException {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		List<InstitutionStudentDTO> students = studentService.filterStudents(institutionId, pageable, null);

		return students;
	}

	@Override
	public List<InstitutionCourseDTO> getInstitutionCourses(int institutionId, Pageable pageable)
			throws EntityNotFoundException {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		List<InstitutionCourseDTO> courses = courseService.filterCourses(institutionId, pageable, null);

		return courses;
	}

	@Override
	public List<InstitutionExamPeriodDTO> getInstitutionExamPeriods(int institutionId, Pageable pageable)
			throws EntityNotFoundException {
		if(!institutionRepo.existsById(institutionId)) throw new EntityNotFoundException();

		List<InstitutionExamPeriodDTO> examPeriods = examPeriodService.filterExamPeriods(institutionId, pageable, null);

		return examPeriods;
	}

}
