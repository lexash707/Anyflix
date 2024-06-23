package com.lex.netflixserije.controllers;

import com.lex.netflixserije.dto.SearchRequest;
import com.lex.netflixserije.models.Korisnik;
import com.lex.netflixserije.models.Serija;
import com.lex.netflixserije.models.Zanr;
import com.lex.netflixserije.repository.ZanrRepository;
import com.lex.netflixserije.services.KorisnikService;
import com.lex.netflixserije.services.SerijaService;
import com.lex.netflixserije.services.ZanrService;
import com.lex.netflixserije.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private SerijaService ss;

    @Autowired
    private ZanrService zs;

    @Autowired
    private ZanrRepository zr;

    @Autowired
    private KorisnikService ks;

    @Autowired
    private JwtUtil ju;

    @PostMapping("/search-filter")
    public List<Serija> homepage(@RequestBody(required = false)SearchRequest searchRequest){

        String search = searchRequest != null ? searchRequest.getSearch() : "";

        if(searchRequest == null){
            return ss.getSve();
        }


        return ss.filter(searchRequest.getSearch(),
                zr.findByIdZanr(searchRequest.getZanr()).orElse(null));
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return  new ModelAndView("login");
    }

    @GetMapping("/registracija")
    public ModelAndView registracija(){
        return new ModelAndView("registracija");
    }

    @GetMapping("/novaSerija")
    public ModelAndView novaSerija(ModelMap model){
        model.addAttribute("zanrovi", zs.getSve());
        return new ModelAndView("nova-serija");}

    @GetMapping("/show-favourites")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Serija> omiljeneSerije(HttpServletRequest request) {
        Korisnik k = ks.nadjiKorisnika(ju.extractUsername(ju.getToken(request).orElseThrow()));
        return ss.nadjiOmiljene(k);
    }

    @GetMapping("/izvestajSerija")
    public ModelAndView izvestajSerije(ModelMap m){
        List<Serija> sveSerije = ss.getSve();
        m.addAttribute("serije", sveSerije);
        return new ModelAndView("izvestaj-detalji");
    }

}
