package com.eUprava.authservice.service;

import com.eUprava.authservice.model.Korisnik;
import com.eUprava.authservice.repository.KorisnikRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private KorisnikRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Korisnik> optionalUser = userRepo.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("There is no user with username " + username);
        }


        Korisnik user = optionalUser.get();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String role = "ROLE_" + user.getUloga().toString();
        grantedAuthorities.add(new SimpleGrantedAuthority(role));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername().trim(),
                user.getLozinka().trim(),
                grantedAuthorities);
    }

//        if(user == null){
//                throw new UsernameNotFoundException("There is no user with username " + username);
//            }
//            else {
//                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//                String role = "ROLE_" + user.getUloga().toString();
//                grantedAuthorities.add(new SimpleGrantedAuthority(role));
//                return new org.springframework.security.core.userdetails.User(
//                        user.getUsername().trim(),
//                        user.getLozinka().trim(),
//                        grantedAuthorities);
//            }

}
