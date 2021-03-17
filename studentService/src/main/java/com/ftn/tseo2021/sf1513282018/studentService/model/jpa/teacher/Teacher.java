package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;

@Entity
@Table(name = "teacher")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "first_name")
	private String firstName;
    
    @Column(name = "last_name")
	private String lastName;
    
    @Column(name = "address")
	private String address;
    
    @Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "teacher_title_id", referencedColumnName = "teacher_title_id", nullable = false)
    private TeacherTitle teacherTitle;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacher")
    private Set<Teaching> teacherTeachingCourse;

    @ManyToOne
    @JoinColumn(name = "institution_id", referencedColumnName = "institution_id", nullable = false)
    private Institution institution;
}
