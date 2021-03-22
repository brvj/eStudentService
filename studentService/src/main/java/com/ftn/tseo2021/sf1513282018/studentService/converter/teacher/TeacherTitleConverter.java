package com.ftn.tseo2021.sf1513282018.studentService.converter.teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherTitleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherTitleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherTitleConverter implements DtoConverter<TeacherTitle, TeacherTitleDTO, DefaultTeacherTitleDTO> {

	@Autowired
	private TeacherConverter teacherConverter;

	@Override
	public TeacherTitle convertToJPA(TeacherTitleDTO source) {
		if(source instanceof DefaultTeacherTitleDTO) return convertToJPA((DefaultTeacherTitleDTO) source);
		else throw new IllegalArgumentException(String.format("Converting from %s type is not supported!", source.getClass().toString()));
	}

	@Override
	public List<TeacherTitle> convertToJPA(List<? extends TeacherTitleDTO> sources) {
		List<TeacherTitle> result = new ArrayList<>();

		if(sources != null){
			if(sources.get(0) instanceof DefaultTeacherTitleDTO){
				sources.stream().forEach(teacherTitleDTO -> {
					result.add(convertToJPA((DefaultTeacherTitleDTO) teacherTitleDTO));
				});
			}
			else {
				throw new IllegalArgumentException(String.format("Converting from %s type is not supported!", sources.get(0).getClass().toString()));
			}
		}
		return result;
	}

	@Override
	public <T extends TeacherTitleDTO> T convertToDTO(TeacherTitle source, Class<? extends TeacherTitleDTO> returnType) {
		if(returnType == DefaultTeacherTitleDTO.class) return (T) convertToDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends TeacherTitleDTO> convertToDTO(List<TeacherTitle> sources, Class<? extends TeacherTitleDTO> returnType) {
		if(returnType == DefaultTeacherTitleDTO.class){
			List<DefaultTeacherTitleDTO> result = new ArrayList<>();
			if(sources != null){
				sources.stream().forEach(teacherTitle -> {
					result.add(convertToDefaultTeacherTitleDTO(teacherTitle));
				});
			}
			return result;
		}else {
			throw new IllegalArgumentException(String.format("Converting from %s type is not supported!", sources.get(0).getClass().toString()));
		}
	}

	@Override
	public DefaultTeacherTitleDTO convertToDTO(TeacherTitle source) {
		return convertToDefaultTeacherTitleDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultTeacherTitleDTO> convertToDTO(List<TeacherTitle> sources) {
		return (List<DefaultTeacherTitleDTO>) convertToDTO(sources, DefaultTeacherTitleDTO.class);
	}

	private DefaultTeacherTitleDTO convertToDefaultTeacherTitleDTO(TeacherTitle source){
		if(source == null) throw new NullPointerException();

		if(source.getTeachers() != null) {
			List<DefaultTeacherDTO> teachers = teacherConverter.convertToDTO(new ArrayList<>(source.getTeachers()));
			DefaultTeacherTitleDTO dto = new DefaultTeacherTitleDTO(source.getId(), source.getName(), teachers);

			return dto;
		}
		return null;
	}

	private TeacherTitle convertToJPA(DefaultTeacherTitleDTO source){
		if(source == null) throw new NullPointerException();

		if(source.getTeachers() != null){
			Set<Teacher> teachers = (Set<Teacher>) teacherConverter.convertToJPA(source.getTeachers());
			TeacherTitle teacherTitle = new TeacherTitle(source.getId(), source.getName(), teachers);

			return teacherTitle;
		}
		return null;
	}

}
