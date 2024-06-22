package com.lex.netflixserije.services;

import com.lex.netflixserije.models.Korisnik;
import com.lex.netflixserije.models.Serija;
import com.lex.netflixserije.models.TipKorisnika;
import com.lex.netflixserije.repository.KorisnikRepository;
import com.lex.netflixserije.repository.TipKorisnikaRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KorisnikService {
    @Autowired
    private KorisnikRepository kr;

    @Autowired
    private TipKorisnikaRepository tkr;

    public List<Korisnik> getSve(){
        return kr.findAll();
    }

    public Korisnik nadjiKorisnika(String username, String password){
        Optional<Korisnik> korisnik = kr.findKorisnikByUsernameAndPassword(username,password);
        return korisnik.orElse(null);
    }

    public Korisnik nadjiKorisnika(String username){
        Optional<Korisnik> korisnik = kr.findKorisnikByUsername(username);
        return korisnik.orElse(null);
    }
    public void sacuvajKorisnika(Korisnik k, String idTipa){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        k.setPassword(encoder.encode(k.getPassword()));
        TipKorisnika tip= tkr.findById(Integer.parseInt(idTipa)).orElseThrow();
        k.setTipkorisnika(tip);
        kr.save(k);
    }
    public void dodajOmiljenu(Korisnik k, Serija s){
        k.getOmiljene().add(s);
        kr.save(k);
    }

    public void logout(HttpServletRequest request){
        SecurityContextHolder.getContext().setAuthentication(null);
        request.getSession().removeAttribute("korisnik");
    }
}
