package com.ftn.euprava.dom.repository;

import com.ftn.euprava.dom.model.Kartica;
import com.ftn.euprava.dom.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUsername(String username);
    List<Student> findByBodoviGreaterThanAndSobaIsNull(double bodovi);
    Optional<Student> findByJmbg(String jmbg);
    List<Student> findByBodoviGreaterThanOrderByBodoviDesc(double bodovi);






}

