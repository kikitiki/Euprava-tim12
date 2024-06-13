package com.zdravstvo.demo.repo;

import com.zdravstvo.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Struct;

public interface StudentRepository extends JpaRepository<Student,Long> {

        Student findByJmbg(String jmbg);
        Student findByUsername(String username);
}
