package com.lex.netflixserije.repository;

import com.lex.netflixserije.models.Korisnik;
import com.lex.netflixserije.models.Serija;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerijaRepository extends JpaRepository<Serija, Integer> {

    public List<Serija> findSerijaByKorisniciContaining(Korisnik k);

    public List<Serija> findSerijasByIdSerijeIn(List<Integer> ids);
}
