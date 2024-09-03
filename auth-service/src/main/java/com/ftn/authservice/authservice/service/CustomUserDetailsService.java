package com.ftn.authservice.authservice.service;

import com.ftn.authservice.authservice.entity.Kartica;
import com.ftn.authservice.authservice.entity.Korisnik;
import com.ftn.authservice.authservice.entity.KorisnikDTO;
import com.ftn.authservice.authservice.entity.StudentDTO;
import com.ftn.authservice.authservice.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private KorisnikRepository userRepo;

    @Autowired
    private RestTemplate restTemplate;

    private final String domServiceUrl = "http://localhost:9000/api/konkursi";

    public void registerUser(KorisnikDTO korisnikDTO) {
        // Mapiranje KorisnikDTO u StudentDTO
        StudentDTO studentDTO = mapToStudentDTO(korisnikDTO);

        // Postavljanje default vrednosti za StudentDTO
        studentDTO.setGodinaStudiranja(0); // Primer vrednosti, prilagodi po potrebi
        studentDTO.setOsvojeniBodovi(0);    // Primer vrednosti, prilagodi po potrebi
        studentDTO.setProsek(0.0);          // Primer vrednosti, prilagodi po potrebi
        studentDTO.setKartica(Kartica.SAMOFINANSIRANJE);  // Primer vrednosti, prilagodi po potrebi
        studentDTO.setBodovi(0.0);          // Primer vrednosti, prilagodi po potrebi

        // Kreiraj HttpEntity za POST zahtev
        HttpEntity<StudentDTO> request = new HttpEntity<>(studentDTO);

        try {
            // Pošaljite POST zahtev prema `dom` servisu
            restTemplate.exchange(domServiceUrl, HttpMethod.POST, request, Void.class);
        } catch (Exception e) {
            // Loguj grešku ako je došlo do problema
            e.printStackTrace();
        }
    }

    private StudentDTO mapToStudentDTO(KorisnikDTO korisnikDTO) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setUsername(korisnikDTO.getUsername());
        studentDTO.setPassword(korisnikDTO.getPassword());
        studentDTO.setIme(korisnikDTO.getIme());
        studentDTO.setPrezime(korisnikDTO.getPrezime());
        studentDTO.setJmbg(korisnikDTO.getJmbg());
        return studentDTO;
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Korisnik user = userRepo.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("There is no user with username " + username);
        }
        else {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            String role = "ROLE_" + user.getUloga().toString();
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername().trim(),
                    user.getLozinka().trim(),
                    grantedAuthorities);
        }
    }
}
