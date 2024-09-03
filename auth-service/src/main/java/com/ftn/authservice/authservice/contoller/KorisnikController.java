package com.ftn.authservice.authservice.contoller;


import com.ftn.authservice.authservice.entity.Korisnik;
import com.ftn.authservice.authservice.entity.KorisnikDTO;
import com.ftn.authservice.authservice.repository.KorisnikRepository;
import com.ftn.authservice.authservice.security.TokenUtils;
import com.ftn.authservice.authservice.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class KorisnikController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    CustomUserDetailsService customUserDetailsService;


    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody KorisnikDTO userDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(404).build();
        } catch (LockedException ex) {
            // do something
        } catch (DisabledException ex) {
            return ResponseEntity.status(403).build();
        }

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());

//            User user = userService.findByUsername(userDto.getUsername());
//            if(!user.isEnabled()) {
//                return ResponseEntity.status(404).build();
//            }

            return ResponseEntity.ok(tokenUtils.generateToken(userDetails));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
    }


    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody KorisnikDTO korisnikDTO) {
        // Provera da li već postoji korisnik sa istim korisničkim imenom
        if (korisnikRepository.findByUsername(korisnikDTO.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Korisničko ime je već zauzeto!");
        }

        // Provera da li JMBG već postoji
        if (korisnikRepository.findByJmbg(korisnikDTO.getJmbg()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Korisnik sa datim JMBG-om već postoji!");
        }

        // Kreiranje novog korisnika
        Korisnik korisnik = new Korisnik();
        korisnik.setIme(korisnikDTO.getIme());
        korisnik.setPrezime(korisnikDTO.getPrezime());
        korisnik.setUsername(korisnikDTO.getUsername());
        korisnik.setLozinka(passwordEncoder.encode(korisnikDTO.getPassword()));
        korisnik.setJmbg(korisnikDTO.getJmbg());
        korisnik.setUloga("STUDENT"); // Postavi osnovnu ulogu, npr. "USER"
        korisnik.setEnabled(true); // Omogući korisnika

        // Čuvanje korisnika u bazi auth servisa
        korisnikRepository.save(korisnik);

        try {
            // Pozovi registerUser metodu iz CustomUserDetailsService
            customUserDetailsService.registerUser(korisnikDTO);
        } catch (Exception e) {
            // Loguj grešku ako je došlo do problema
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška pri registraciji studenta.");
        }

        return ResponseEntity.ok("Korisnik uspešno registrovan!");
    }}