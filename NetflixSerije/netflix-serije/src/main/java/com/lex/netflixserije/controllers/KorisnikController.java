package com.lex.netflixserije.controllers;

import java.util.List;

import com.lex.netflixserije.security.UserDetailsImpl;
import com.lex.netflixserije.services.KorisnikService;
import com.lex.netflixserije.services.SerijaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.lex.netflixserije.models.Korisnik;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
@RequestMapping(path="/korisnici")
public class KorisnikController {

    @Autowired
    private KorisnikService ks;

    @Autowired
    private SerijaService ss;

    @Autowired
    private AuthenticationManager authManager;

    @GetMapping("/test")
    public List<Korisnik> getAll(){
        return ks.getSve();
    }



    @PostMapping(path = "/login")
    public ModelAndView login(ModelMap model, HttpServletRequest request, RedirectAttributes ra, @RequestParam(name="username") String username, @RequestParam(name="password") String password){
        Authentication auth = null;

        try {
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, password);
            auth = authManager.authenticate(authReq);
        }
        catch(RuntimeException e){
            ra.addFlashAttribute("fail", true);
            return new ModelAndView("redirect:/login");
        }

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        String un = ((UserDetailsImpl) sc.getAuthentication().getPrincipal()).getUsername();

        Korisnik korisnik = ks.nadjiKorisnika(un);
        request.getSession().setAttribute("korisnik", korisnik);

        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        return new ModelAndView("redirect:/", model);
    }

    @RequestMapping(value = "/dodajKorisnika", method = RequestMethod.POST)
    public ModelAndView saveKorisnik(Korisnik k, Errors e, RedirectAttributes ra, @RequestParam(name="tipKorisnika")String tk){
        if(e.hasErrors())
            return new ModelAndView("redirect:/dodajKorisnika");

        ks.sacuvajKorisnika(k, tk);
        ra.addAttribute("poruka", "Korisnik uspesno dodat");

        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ModelAndView logout(HttpServletRequest request){
        ks.logout(request);
        return new ModelAndView("redirect:/");
    }
}
