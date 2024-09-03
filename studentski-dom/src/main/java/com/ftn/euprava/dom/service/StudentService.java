package com.ftn.euprava.dom.service;

import com.ftn.euprava.dom.dto.StudentDTO;
import com.ftn.euprava.dom.model.Kartica;
import com.ftn.euprava.dom.model.Konkurs;
import com.ftn.euprava.dom.model.Student;
import com.ftn.euprava.dom.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;



    @Autowired
    private KonkursService konkursService;

    @Autowired
    private RestTemplate restTemplate;

    private final String fakultetServiceUrl = "http://localhost:9009/api/studenti";

    public Optional<StudentDTO> getStudentByJmbg(String jmbg) {
        StudentDTO fakultetStudentDTO = restTemplate.getForObject(fakultetServiceUrl + "/by-jmbg/" + jmbg, StudentDTO.class);
        if (fakultetStudentDTO != null) {
            return Optional.of(fakultetStudentDTO);
        }
        return Optional.empty();
    }

    public void prijaviStudentaNaKonkurs(StudentDTO studentDTO) {
        // Povuci podatke o studentu iz fakultet servisa
        StudentDTO fakultetStudentDTO = restTemplate.getForObject(fakultetServiceUrl + "/by-jmbg/" + studentDTO.getJmbg(), StudentDTO.class);

        if (fakultetStudentDTO == null) {
            throw new IllegalArgumentException("Student sa JMBG-om " + studentDTO.getJmbg() + " ne postoji u fakultet servisu");
        }

        // Pronađi studenta u dom servisu na osnovu JMBG-a
        Student domStudent = studentRepository.findByJmbg(studentDTO.getJmbg())
                .orElseThrow(() -> new IllegalArgumentException("Student sa JMBG-om " + studentDTO.getJmbg() + " ne postoji u dom servisu"));


        // Provera da li je student već prijavljen na konkurs
        if (domStudent.getKonkurs() != null && domStudent.getKonkurs().getId() != 0) {
            throw new IllegalArgumentException("Student je već prijavljen na konkurs.");
        }


        // Uporedite JMBG-ove
        if (!fakultetStudentDTO.getJmbg().equals(domStudent.getJmbg())) {
            throw new IllegalArgumentException("JMBG se ne poklapa sa studentom u dom servisu");
        }

        // Ažuriranje svih podataka o studentu
        domStudent.setIme(fakultetStudentDTO.getIme());
        domStudent.setPrezime(fakultetStudentDTO.getPrezime());
        domStudent.setGodinaStudiranja(fakultetStudentDTO.getGodinaStudiranja());
        domStudent.setOsvojeniBodovi(fakultetStudentDTO.getOsvojeniBodovi());
        domStudent.setProsek(fakultetStudentDTO.getProsek());

        double bodovi = (fakultetStudentDTO.getProsek() + (double) fakultetStudentDTO.getOsvojeniBodovi()) / fakultetStudentDTO.getGodinaStudiranja();
        domStudent.setBodovi(bodovi);

        // Dodeljivanje konkursId i ažuriranje konkursa
        Konkurs konkurs = konkursService.findKonkursById(studentDTO.getKonkursId())
                .orElseThrow(() -> new IllegalArgumentException("Konkurs sa datim ID-om ne postoji"));
        domStudent.setKonkurs(konkurs);

        studentRepository.save(domStudent);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            return studentRepository.save(updatedStudent);
        }
        return null;
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public void registerStudent(StudentDTO studentDTO) {
        // Proveri da li student već postoji
        Optional<Student> existingStudent = studentRepository.findByJmbg(studentDTO.getJmbg());
        if (existingStudent.isPresent()) {
            throw new IllegalArgumentException("Student sa JMBG-om " + studentDTO.getJmbg() + " već postoji.");
        }

        // Kreiraj novog studenta
        Student newStudent = new Student();
        newStudent.setUsername(studentDTO.getUsername());
        newStudent.setPassword(studentDTO.getPassword()); // Obavezno hash-uj lozinku u stvarnoj aplikaciji
        newStudent.setIme(studentDTO.getIme());
        newStudent.setPrezime(studentDTO.getPrezime());
        newStudent.setJmbg(studentDTO.getJmbg());

        // Postavi default vrednosti za primitivne tipove
        newStudent.setGodinaStudiranja(studentDTO.getGodinaStudiranja());
        newStudent.setOsvojeniBodovi(studentDTO.getOsvojeniBodovi());
        newStudent.setProsek(studentDTO.getProsek());
        newStudent.setKartica(studentDTO.getKartica());
        newStudent.setBodovi(studentDTO.getBodovi());

        studentRepository.save(newStudent);
    }


}
