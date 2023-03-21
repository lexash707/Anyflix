package com.lex.netflixserije.controllers;

import com.lex.netflixserije.models.Korisnik;
import com.lex.netflixserije.models.Serija;
import com.lex.netflixserije.models.Zanr;
import com.lex.netflixserije.services.SerijaService;
import com.lex.netflixserije.services.ZanrService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private SerijaService ss;

    @Autowired
    private ZanrService zs;

    @GetMapping("/")
    public ModelAndView homepage(ModelMap model, @RequestParam(required = false) String pretraga, @RequestParam(required = false) Zanr zanrovi){
        model.addAttribute("serije", ss.filter(pretraga, zanrovi));
        model.addAttribute("zanrovi", zs.getSve());
        return new ModelAndView("index");
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

    @GetMapping("/omiljene")
    public ModelAndView omiljeneSerije(HttpServletRequest request, ModelMap m){
        Korisnik k = (Korisnik)request.getSession().getAttribute("korisnik");
        m.addAttribute("omiljene", ss.nadjiOmiljene(k));
        return new ModelAndView("omiljene");}

    @GetMapping("/izvestajSerija")
    public ModelAndView izvestajSerije(ModelMap m){
        List<Serija> sveSerije = ss.getSve();
        m.addAttribute("serije", sveSerije);
        return new ModelAndView("izvestaj-detalji");
    }

}
