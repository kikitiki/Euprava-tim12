package com.zdravstvo.demo.repo;

import com.zdravstvo.demo.model.Doktor;
import com.zdravstvo.demo.model.Student;
import com.zdravstvo.demo.model.Termin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TerminRepository extends JpaRepository<Termin,Long> {
    List<Termin> findAllByStudnet(Student student);
    List<Termin> findAllByDoktor(Doktor doktor);
}
