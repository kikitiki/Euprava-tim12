package com.ftn.euprava.doktor.controller;

import com.ftn.euprava.doktor.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doktor")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Novi endpoint za proveru lekarskog pregleda na osnovu JMBG-a
    @GetMapping("/lekarski")
    public ResponseEntity<Boolean> imaLekarski(@RequestParam String jmbg) {
        try {
            boolean imaLekarski = studentService.proveriLekarski(jmbg);
            return ResponseEntity.ok(imaLekarski);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}