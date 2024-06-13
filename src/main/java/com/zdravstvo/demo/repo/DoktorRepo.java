package com.zdravstvo.demo.repo;

import com.zdravstvo.demo.model.Doktor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.transform.sax.SAXResult;
import java.util.List;

@Repository
public interface DoktorRepo extends JpaRepository<Doktor,Long> {

    Doktor findByUsername(String username);
    List<Doktor> findAllBySpecijalnost(String specijalnost);
}
