package com.zdravstvo.demo.service;

import com.zdravstvo.demo.model.Doktor;
import com.zdravstvo.demo.model.Student;
import com.zdravstvo.demo.repo.DoktorRepo;
import com.zdravstvo.demo.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private DoktorRepo doktorRepo;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Student student = null;
        Doktor doktor = null;

        if (studentRepository.findByUsername(username) !=null){
            student= studentRepository.findByUsername(username);
        } else if (doktorRepo.findByUsername(username) != null) {
            doktor = doktorRepo.findByUsername(username);
        }

        if(student == null && doktor == null){
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.",username));
        }else{
            List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
            if(student != null){
                String role = "ROLE_STUDENT";
                grantedAuthorityList.add(new SimpleGrantedAuthority(role));
                return new org.springframework.security.core.userdetails.User(
                        student.getUsername().trim(),
                        student.getLozinka().trim(),
                        grantedAuthorityList);
            }else {
                String role = "ROLE_DOKTOR";
                grantedAuthorityList.add(new SimpleGrantedAuthority(role));
                return new org.springframework.security.core.userdetails.User(
                        doktor.getUsername().trim(),
                        doktor.getLozinka().trim(),
                        grantedAuthorityList);
            }
        }
    }
}
