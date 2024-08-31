package com.ftn.euprava.fakultet.service;

import com.ftn.euprava.fakultet.dto.StudentDTO;
import com.ftn.euprava.fakultet.model.Student;
import com.ftn.euprava.fakultet.repository.StudentRepository;
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

    private StudentDTO convertToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setIme(student.getIme());
        studentDTO.setPrezime(student.getPrezime());
        studentDTO.setJmbg(student.getJmbg());
        studentDTO.setGodinaStudiranja(student.getGodinaStudiranja());
        studentDTO.setOsvojeniBodovi(student.getOsvojeniBodovi());
        studentDTO.setProsek(student.getProsek());
        return studentDTO;
    }

}