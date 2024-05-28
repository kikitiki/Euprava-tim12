package com.eUprava.authservice.controller;

import com.eUprava.authservice.model.KorisnikDto;
import com.eUprava.authservice.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("api/auth/")
public class KorisnikController {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody KorisnikDto userDto) {
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

            return ResponseEntity.ok(tokenUtils.generateToken(userDetails));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
