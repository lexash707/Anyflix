package com.lex.netflixserije.repository;

import com.lex.netflixserije.models.Serija;
import com.lex.netflixserije.models.Zanr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZanrRepository extends JpaRepository<Zanr, Integer> {
    public Optional<Zanr> findByNaziv(String naziv);

    public Optional<Zanr> findByIdZanr(int id);
}
