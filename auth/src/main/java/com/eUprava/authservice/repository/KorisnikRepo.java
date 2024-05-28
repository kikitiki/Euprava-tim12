package com.eUprava.authservice.repository;

import com.eUprava.authservice.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface KorisnikRepo extends JpaRepository<Korisnik,Long> {
//    Korisnik findByUsername(String username);
Optional<Korisnik> findByUsername(String username);
}
