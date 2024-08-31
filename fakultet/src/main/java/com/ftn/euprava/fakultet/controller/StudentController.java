package com.ftn.euprava.fakultet.controller;


import com.ftn.euprava.fakultet.dto.StudentDTO;
import com.ftn.euprava.fakultet.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studenti")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/by-jmbg/{jmbg}")
    public ResponseEntity<StudentDTO> getStudentByJmbg(@PathVariable String jmbg) {
        try {
            StudentDTO studentDTO = studentService.getStudentByJmbg(jmbg);
            return new ResponseEntity<>(studentDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}