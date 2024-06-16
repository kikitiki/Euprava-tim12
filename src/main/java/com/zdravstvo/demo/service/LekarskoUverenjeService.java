package com.zdravstvo.demo.service;

import com.zdravstvo.demo.exception.BadRequestException;
import com.zdravstvo.demo.exception.NotAcceptableException;
import com.zdravstvo.demo.model.LekarskoUverenje;
import com.zdravstvo.demo.model.Pol;
import com.zdravstvo.demo.model.Student;
import com.zdravstvo.demo.model.dto.LekarskiIzvestajRequest;
import com.zdravstvo.demo.model.dto.LekarskoUverenjeRequest;
import com.zdravstvo.demo.model.dto.LekarskoUverenjeResponse;
import com.zdravstvo.demo.model.dto.StudentResponse;
import com.zdravstvo.demo.repo.LekarskoUverenjeRepository;
import com.zdravstvo.demo.repo.StudentRepository;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LekarskoUverenjeService {
    @Autowired
    private LekarskoUverenjeRepository lekarskoUverenjeRepository;

    @Autowired
    private StudentRepository studentRepository;

    public LekarskoUverenjeResponse addLekarskoUverenje(LekarskoUverenjeRequest request) throws NotAcceptableException {

        Student student =studentRepository.findByJmbg(request.getJmbg());
        List<LekarskoUverenje> uverenja =lekarskoUverenjeRepository.findAll();
        for (LekarskoUverenje lu: uverenja){
            if (lu.getStudentJmbg().equals(student.getJmbg())){
                throw new BadRequestException("Vec postoji lekarsko uverenje");
            }
        }

        if (proveraPregleda(student)){
            LekarskoUverenjeResponse response =new LekarskoUverenjeResponse("Izdaje se lekarsko uverenje"
            + request.getIme() + " " + request.getPrezime() + "JMBG: " + request.getJmbg()
            + ".Datum izdavanja : " + LocalDate.now());

            LekarskoUverenje lekarskoUverenje = new LekarskoUverenje(request.getJmbg(), response.getOpis());
            lekarskoUverenjeRepository.save(lekarskoUverenje);
            return response;
        }else{
            throw new NotAcceptableException("Student nije uradio sve potrebne preglede!");
        }

    }

    public List<StudentResponse> getStudentiZaUverenje(){
        List<StudentResponse> studenti =new ArrayList<>();
        List<Student> sviStudenti = studentRepository.findAll();
        for (Student s : sviStudenti){
            if (proveraPregleda(s)){
                StudentResponse studentForList =new StudentResponse();
                studentForList.setId(s.getId());
                studentForList.setIme(s.getIme());
                studentForList.setPrezime(s.getPrezime());
                studentForList.setJmbg(s.getJmbg());
                studenti.add(studentForList);
            }
        }
        return studenti;
    }

    public List<LekarskoUverenjeResponse> getAll(){
        List<LekarskoUverenjeResponse> response =new ArrayList<>();
        List<LekarskoUverenje> uverenja =lekarskoUverenjeRepository.findAll();

        for (LekarskoUverenje u : uverenja){
            LekarskoUverenjeResponse lur =new LekarskoUverenjeResponse();
            lur.setOpis(u.getPoruka());
            response.add(lur);
        }

        return response;
    }

    public Boolean proveraUverenjaZaStudenta(final String jmbg) {
        LekarskoUverenje uverenje = lekarskoUverenjeRepository.findByStudentJmbg(jmbg);
        if(uverenje == null) {
            return false;
        }
        return true;
    }

    private Boolean proveraPregleda(Student student){
        Boolean uradio = false;

        if (student.getPol().equals(Pol.MUSKI)){
            if (student.getStomatolog() && student.getOpstaPraksa()){
                uradio = true;
            }
        }


        if (student.getPol().equals(Pol.ZENSKI)){
            if (student.getStomatolog() && student.getOpstaPraksa() && student.getGinekolog()){
                uradio = true;
            }
        }

        return  uradio;
    }

}
