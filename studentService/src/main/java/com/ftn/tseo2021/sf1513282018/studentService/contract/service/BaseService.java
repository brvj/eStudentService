package com.ftn.tseo2021.sf1513282018.studentService.contract.service;

import java.util.List;

public interface BaseService<T, G> {
    List<G> getAll();
    G findById(int id);
    G save(T t);
    G deleteById(int id);
}
