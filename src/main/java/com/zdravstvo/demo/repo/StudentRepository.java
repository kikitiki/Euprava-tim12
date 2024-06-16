package com.zdravstvo.demo.repo;

import com.zdravstvo.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Struct;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

        Student findByJmbg(String jmbg);
        Student findByUsername(String username);
}
