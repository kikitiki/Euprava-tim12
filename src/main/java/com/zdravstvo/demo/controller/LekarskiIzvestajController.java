package com.zdravstvo.demo.controller;

import com.zdravstvo.demo.model.LekarskiIzvestaj;
import com.zdravstvo.demo.model.dto.LekarskiIzvestajRequest;
import com.zdravstvo.demo.model.dto.LekarskiIzvestajResponse;
import com.zdravstvo.demo.service.LekarskiIzvestajService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Validated
@RequestMapping("/api/izvestaji")
public class LekarskiIzvestajController {
    @Autowired
    LekarskiIzvestajService lekarskiIzvestajService;

    @PreAuthorize("hasRole('DOKTOR')")
    @PostMapping
    public HttpStatus addLekarskiIzvestaj(@RequestBody LekarskiIzvestajRequest request){
        if (lekarskiIzvestajService.addIzvestaj(request)){
            return HttpStatus.CREATED;
        }else {
            return HttpStatus.BAD_REQUEST;
        }
    }


    @GetMapping("/by-doktor")
    public ResponseEntity<List<LekarskiIzvestajResponse>> getIzvestajiByLoggedDoctor(Authentication authentication){
        return ResponseEntity.ok().body(lekarskiIzvestajService.getIzvestajiByDoktor(authentication));
    }
}
