package com.zdravstvo.demo.controller;

import com.zdravstvo.demo.service.DoktorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Validated
@RequestMapping("/api/doktor")
public class DoktorController {
    @Autowired
    private DoktorService doktorService;

    @GetMapping("/{username}")
    public ResponseEntity<Boolean> checkDoktor(@PathVariable String username){
        return ResponseEntity.ok(doktorService.doktroOpstePrakse(username));
    }
}
