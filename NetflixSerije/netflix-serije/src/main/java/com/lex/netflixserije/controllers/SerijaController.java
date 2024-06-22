package com.lex.netflixserije.controllers;

import com.lex.netflixserije.models.Korisnik;
import com.lex.netflixserije.models.Ocena;
import com.lex.netflixserije.models.Serija;
import com.lex.netflixserije.repository.SerijaRepository;
import com.lex.netflixserije.services.KorisnikService;
import com.lex.netflixserije.services.OcenaService;
import com.lex.netflixserije.services.SerijaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/series")
public class SerijaController {

    @Autowired
    private SerijaService ss;

    @Autowired
    private KorisnikService ks;

    @Autowired
    private OcenaService os;

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Serija> getSve(){
        return ss.getSve();
    }

@RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<HttpServletResponse> saveSerija(Serija s, Errors e, Model m, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        try {
            ss.sacuvajSeriju(s, multipartFile);
        }catch (ValidationException ve){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/details/{id}")
    public ModelAndView detalji(@PathVariable int id, ModelMap model){
        Serija s = ss.getId(id);
        List<Ocena> ocene = s.getOcene();
        System.out.println(ocene);
        model.addAttribute("s", s);
        model.addAttribute("ocene", ocene);
        return new ModelAndView("details");
    }

    @RequestMapping(value = "dodajOmiljenu", method = RequestMethod.POST)
    public ModelAndView dodajOmiljenu(@RequestParam(name = "korisnik") Korisnik k, @RequestParam(name = "serija") Serija s){
        ks.dodajOmiljenu(k, s);
        return new ModelAndView("redirect:details/" + s.getIdSerije());
    }

    @RequestMapping(value = "/oceniSeriju", method = RequestMethod.POST)
    public ModelAndView oceniSeriju(@RequestParam(name = "korisnik") Korisnik k, @RequestParam(name = "ocena") int ocena, @RequestParam(name = "komentar") String komentar, @RequestParam(name= "serija") Serija serija){
        Ocena o = Ocena.builder().korisnik(k).ocena(ocena).komentar(komentar).serija(serija).build();
        os.sacuvajOcenu(o);
        return new ModelAndView("redirect:details/" + serija.getIdSerije());
    }

    @RequestMapping(value="/report", method = RequestMethod.POST)
    public void napraviIzvestaj(HttpServletResponse response, @RequestParam("selektovaneSerije") List<Integer> selektovane) throws Exception{
        JasperPrint jasperPrint = ss.izvestaj(selektovane);
        response.setContentType("application/x-download");
        response.addHeader("Content-disposition", "attachment; filename=Serije.pdf");
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
    }
}
