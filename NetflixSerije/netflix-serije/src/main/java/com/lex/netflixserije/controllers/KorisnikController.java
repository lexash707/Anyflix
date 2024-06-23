package com.lex.netflixserije.controllers;

import java.util.List;

import com.lex.netflixserije.dto.LoginRequest;
import com.lex.netflixserije.dto.LoginResponse;
import com.lex.netflixserije.security.CustomUserDetailsService;
import com.lex.netflixserije.security.UserDetailsImpl;
import com.lex.netflixserije.services.KorisnikService;
import com.lex.netflixserije.services.SerijaService;
import com.lex.netflixserije.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/user")
public class KorisnikController {

    @Autowired
    private KorisnikService ks;

    @Autowired
    private SerijaService ss;

    @Autowired
    private AuthenticationManager authManager;

    private final CustomUserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final KorisnikService userService;

    @GetMapping("/test")
    public List<Korisnik> getAll(){
        return ks.getSve();
    }



    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(loginRequest.getUsername());
        Korisnik user = ks.nadjiKorisnika(userDetails.getUsername());
        return LoginResponse.builder().token(jwtUtil.generateToken(userDetails)).user(user).build();
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
