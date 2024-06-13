package com.zdravstvo.demo.repo;

import com.zdravstvo.demo.model.LekarskoUverenje;
import com.zdravstvo.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LekarskoUverenjeRepository extends JpaRepository<LekarskoUverenje,Long> {
    LekarskoUverenje findByStudentJmbg(String jmbg);
}
