package com.ftn.tseo2021.sf1513282018.studentService.service.institution;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserFilterOptions;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.CourseService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamPeriodService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.user.UserService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAny;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeSuperadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import java.util.List;
import java.util.function.Function;

@Service
public class DefaultInstitutionService implements InstitutionService {

	@Autowired
	private InstitutionRepository institutionRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private ExamPeriodService examPeriodService;

	@Autowired
	private DtoConverter<Institution, InstitutionDTO, DefaultInstitutionDTO> institutionConverter;
	
	@Autowired
	private PersonalizedAuthorizator authorizator;
	
	@AuthorizeAny
	@Override
	public DefaultInstitutionDTO getOne(Integer id) {
		authorizator.assertPrincipalIsFromInstitution(id, PersonalizedAccessDeniedException.class);
		
		Institution institution = institutionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		return institutionConverter.convertToDTO(institution);
	}

	@AuthorizeSuperadmin
	@Override
	public Integer create(DefaultInstitutionDTO dto) {
		Institution institution = institutionConverter.convertToJPA(dto);

		institution = institutionRepo.save(institution);

		return institution.getId();
	}

	@AuthorizeSuperadmin
	@Override
	public void update(Integer id, DefaultInstitutionDTO dto) {
		Institution i = institutionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		Institution iNew = institutionConverter.convertToJPA(dto);
		
		i.setName(iNew.getName());
		i.setAddress(iNew.getAddress());
		i.setPhoneNumber(iNew.getPhoneNumber());
		institutionRepo.save(i);

	}

	@AuthorizeSuperadmin
	@Override
	public void delete(Integer id) {
		if(!institutionRepo.existsById(id)) throw new ResourceNotFoundException();

		institutionRepo.deleteById(id);
	}
	
//	No need to explicitly secure methods below since they completely depend on secured methods of another services
	
	@Override
	public Page<InstitutionUserDTO> getInstitutionUsers(int institutionId, Pageable pageable) {
		if(!institutionRepo.existsById(institutionId)) throw new ResourceNotFoundException();

		Page<InstitutionUserDTO> page = userService.filterUsers(institutionId, pageable, null);

		return page;
	}
	
	@Override
	public Page<InstitutionUserDTO> getInstitutionAdmins(int institutionId, Pageable pageable, UserFilterOptions filterOptions) {
		if(!institutionRepo.existsById(institutionId)) throw new ResourceNotFoundException();

		return userService.filterAdminsForInstitution(institutionId, pageable, filterOptions);
	}

	@Override
	public Page<InstitutionTeacherDTO> getInstitutionTeachers(int institutionId, Pageable pageable) {
		if(!institutionRepo.existsById(institutionId)) throw new ResourceNotFoundException();

		Page<InstitutionTeacherDTO> teachers = teacherService.filterTeachers(institutionId, pageable, null);

		return teachers;
	}

	@Override
	public Page<InstitutionStudentDTO> getInstitutionStudents(int institutionId, Pageable pageable) {
		if(!institutionRepo.existsById(institutionId)) throw new ResourceNotFoundException();

		Page<InstitutionStudentDTO> students = studentService.filterStudents(institutionId, pageable, null);

		return students;
	}

	@Override
	public Page<InstitutionCourseDTO> getInstitutionCourses(int institutionId, Pageable pageable) {
		if(!institutionRepo.existsById(institutionId)) throw new ResourceNotFoundException();

		Page<InstitutionCourseDTO> courses = courseService.filterCourses(institutionId, pageable, null);

		return courses;
	}

	@Override
	public Page<InstitutionExamPeriodDTO> getInstitutionExamPeriods(int institutionId, Pageable pageable) {
		if(!institutionRepo.existsById(institutionId)) throw new ResourceNotFoundException();

		Page<InstitutionExamPeriodDTO> page = examPeriodService.filterExamPeriods(institutionId, pageable, null);

		return page;
	}

}
