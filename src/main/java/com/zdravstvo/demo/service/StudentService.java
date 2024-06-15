package com.zdravstvo.demo.service;

import com.zdravstvo.demo.model.Student;
import com.zdravstvo.demo.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    @Lazy
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(){return new RestTemplate();}

    public Student findByUsername(String username){
        return studentRepository.findByUsername(username);
    }

    public Boolean checkStatusStudenta(String jmbg){
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity request =new HttpEntity(httpHeaders);
        String url ="http://localhost:9090/api/servis/budzet/{jmbg}";

        ResponseEntity<Boolean> response =this.restTemplate.exchange(url, HttpMethod.GET,request, Boolean.class,jmbg);

        if (response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        }else {
            return null;
        }
    }

    public Boolean checkStatusKartice(String jmbg){
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity request =new HttpEntity(httpHeaders);
        String url ="http://localhost:9090/api/servis/kartica/{jmbg}";

        ResponseEntity<Boolean> response =this.restTemplate.exchange(url, HttpMethod.GET,request, Boolean.class,jmbg);

        if (response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        }else {
            return null;
        }
    }

    public void save(Student student){
         studentRepository.save(student);
    }
}
