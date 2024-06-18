package com.zdravstvo.demo.controller;

import com.zdravstvo.demo.model.dto.LekarskoUverenjeRequest;
import com.zdravstvo.demo.model.dto.LekarskoUverenjeResponse;
import com.zdravstvo.demo.model.dto.StudentResponse;
import com.zdravstvo.demo.service.LekarskoUverenjeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Validated
@RequestMapping("/api/lekarsko-uverenje")
public class LekarskoUverenjeController {
    @Autowired
    private LekarskoUverenjeService lekarskoUverenjeService;

    @PostMapping
    @PreAuthorize("hasRole('DOKTOR')")
    public ResponseEntity<LekarskoUverenjeResponse> generate(@RequestBody @Valid LekarskoUverenjeRequest request){
        return ResponseEntity.ok(lekarskoUverenjeService.addLekarskoUverenje(request));
    }


    //provera da li je uradjen lekarski pregeled

    @GetMapping("/{jmbg}")
    public ResponseEntity<Boolean> proveraUverenja(@PathVariable String jmbg){
        if (jmbg == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(lekarskoUverenjeService.proveraUverenjaZaStudenta(jmbg));
    }
    @GetMapping
    public ResponseEntity<List<LekarskoUverenjeResponse>> getAll() {
        return ResponseEntity.ok(lekarskoUverenjeService.getAll());
    }
    @GetMapping("/studenti")
    public ResponseEntity<List<StudentResponse>> getStudents(){
        return ResponseEntity.ok(lekarskoUverenjeService.getStudentiZaUverenje());
    }

}
