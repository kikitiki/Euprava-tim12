package com.eUprava.authservice.service;

import com.eUprava.authservice.model.Korisnik;
import com.eUprava.authservice.repository.KorisnikRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataService {
    @Autowired
    private KorisnikRepo korisnikRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void korisnici(){
        createIfNotExists("Mimi", "Macak", "mimi123", "mimi123", "DOKTOR", false);
        createIfNotExists("Ali", "Mali_macak", "ali123", "ali123", "DOKTOR", false);
        createIfNotExists("Sanja", "Komarcevic", "sanja123", "sanja123", "DOKTOR", false);
        createIfNotExists("Dzimi", "Pas", "dzimi123", "dzimi123", "STUDENT", false);
        createIfNotExists("Toso", "Macak", "toso123", "toso123", "STUDENT", false);
    }

    private void createIfNotExists(String ime, String prezime, String username, String lozinka, String uloga, boolean enabled) {
        Optional<Korisnik> existingUser = korisnikRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            Korisnik korisnik = new Korisnik();
            korisnik.setIme(ime);
            korisnik.setPrezime(prezime);
            korisnik.setUsername(username);
            korisnik.setLozinka(passwordEncoder.encode(lozinka));
            korisnik.setUloga(uloga);
            korisnik.setEnabled(enabled);

            korisnikRepository.save(korisnik);
        }
    }


//    public void korisnici(){
//        Korisnik korisnik = new Korisnik();
//        korisnik.setIme("Mimi");
//        korisnik.setPrezime("Macak");
//        korisnik.setUsername("mimi123");
//        korisnik.setLozinka(passwordEncoder.encode("mimi123"));
//        korisnik.setUloga("DOKTOR");
//        korisnik.setEnabled(false);
//
//        Korisnik korisnik1 = new Korisnik();
//        korisnik1.setIme("Ali");
//        korisnik1.setPrezime("Mali_macak");
//        korisnik1.setUsername("ali123");
//        korisnik1.setLozinka(passwordEncoder.encode("ali123"));
//        korisnik1.setUloga("DOKTOR");
//        korisnik1.setEnabled(false);
//
//        Korisnik doktor2 = new Korisnik();
//        doktor2.setIme("Sanja");
//        doktor2.setPrezime("Komarcevic");
//        doktor2.setUsername("sanja123");
//        doktor2.setLozinka(passwordEncoder.encode("sanja123"));
//        doktor2.setUloga("DOKTOR");
//        doktor2.setEnabled(false);
//
//        Korisnik korisnik2 = new Korisnik();
//        korisnik2.setIme("Dzimi");
//        korisnik2.setPrezime("Pas");
//        korisnik2.setUsername("dzimi123");
//        korisnik2.setLozinka(passwordEncoder.encode("dzimi123"));
//        korisnik2.setUloga("STUDENT");
//        korisnik2.setEnabled(false);
//
//        Korisnik korisnik3 = new Korisnik();
//        korisnik3.setIme("Toso");
//        korisnik3.setPrezime("Macak");
//        korisnik3.setUsername("toso123");
//        korisnik3.setLozinka(passwordEncoder.encode("toso123"));
//        korisnik3.setUloga("STUDENT");
//        korisnik3.setEnabled(false);
//
//
//
//
//        korisnikRepository.save(korisnik);
//        korisnikRepository.save(korisnik1);
//        korisnikRepository.save(doktor2);
//        korisnikRepository.save(korisnik2);
//        korisnikRepository.save(korisnik3);
//    }
}
