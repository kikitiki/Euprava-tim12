package com.zdravstvo.demo.repo;

import com.zdravstvo.demo.model.Doktor;
import com.zdravstvo.demo.model.LekarskiIzvestaj;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LekarskiIzvestajRepository extends JpaRepository<LekarskiIzvestaj,Long> {
    LekarskiIzvestaj findByTermin_Id(Long terminId);
    List<LekarskiIzvestaj> findAllByTermin_Doktor(Doktor doktor);
}
