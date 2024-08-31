package com.ftn.euprava.doktor.repository;

import com.ftn.euprava.doktor.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByJmbg(String jmbg);
}