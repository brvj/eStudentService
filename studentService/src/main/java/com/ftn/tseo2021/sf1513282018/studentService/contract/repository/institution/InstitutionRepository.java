package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {

    Page<Institution> findByNameContaining(String name, Pageable pageable);

    Page<Institution> findByAddressContaining(String address, Pageable pageable);
}
