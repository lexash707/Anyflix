package com.lex.netflixserije.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lex.netflixserije.models.Korisnik;

import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
    public Optional<Korisnik> findKorisnikByUsernameAndPassword(String username, String password);

    public Optional<Korisnik> findKorisnikByUsername(String username);

}
