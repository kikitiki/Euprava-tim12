package com.zdravstvo.demo.service;

import com.zdravstvo.demo.model.Doktor;
import com.zdravstvo.demo.model.SpecijalnostDoktora;
import com.zdravstvo.demo.repo.DoktorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoktorService {
    @Autowired
    private DoktorRepo doktorRepo;

    public Doktor findByUsername(String username){
        return doktorRepo.findByUsername(username);
    }

    public Boolean doktroOpstePrakse(String username){
        Doktor doktor = doktorRepo.findByUsername(username);
        if (doktor.getSpecijalnost().equals(SpecijalnostDoktora.OPSTA_PRAKSA)){
            return true;
        }
        return false;
    }

    public void save(Doktor doktor){
        doktorRepo.save(doktor);
    }
}
