package com.ftn.euprava.doktor.service;

import com.ftn.euprava.doktor.dto.StudentDTO;
import com.ftn.euprava.doktor.model.Student;
import com.ftn.euprava.doktor.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentDTO getStudentByJmbg(String jmbg) {
        Optional<Student> studentOptional = studentRepository.findByJmbg(jmbg);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return convertToDTO(student);
        } else {
            throw new IllegalArgumentException("Student sa JMBG-om " + jmbg + " ne postoji");
        }
    }

    public boolean proveriLekarski(String jmbg) {
        Optional<Student> studentOptional = studentRepository.findByJmbg(jmbg);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return student.isLekarski(); // Vrati true ili false na osnovu vrednosti u bazi
        } else {
            throw new IllegalArgumentException("Student sa JMBG-om " + jmbg + " ne postoji");
        }
    }

    private StudentDTO convertToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setIme(student.getIme());
        studentDTO.setPrezime(student.getPrezime());
        studentDTO.setJmbg(student.getJmbg());
        studentDTO.setLekarski(student.isLekarski());
        return studentDTO;
    }
}

