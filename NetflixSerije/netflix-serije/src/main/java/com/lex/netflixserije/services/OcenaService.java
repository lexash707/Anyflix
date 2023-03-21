package com.lex.netflixserije.services;

import com.lex.netflixserije.models.Korisnik;
import com.lex.netflixserije.models.Ocena;
import com.lex.netflixserije.models.Serija;
import com.lex.netflixserije.repository.OcenaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OcenaService {
    @Autowired
    private OcenaRepository or;
    public void sacuvajOcenu(Ocena o){
        or.save(o);
    }

    public void sacuvajOcenu(Serija s, Korisnik k, int ocena, String komentar){
        Ocena o = Ocena.builder().ocena(ocena).korisnik(k).serija(s).komentar(komentar).build();
        or.save(o);
    }
}
