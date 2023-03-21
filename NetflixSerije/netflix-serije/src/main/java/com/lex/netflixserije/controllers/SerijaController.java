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

@Controller
@RequestMapping(value = "/serije")
public class SerijaController {

    @Autowired
    private SerijaService ss;

    @Autowired
    private KorisnikService ks;

    @Autowired
    private OcenaService os;

    @GetMapping("/sveSerije")
    public ModelAndView getSve(ModelMap model){
        model.addAttribute("serije", ss.getSve());
        return new ModelAndView("index");
    }

@RequestMapping(value = "/dodajSeriju", method = RequestMethod.POST)
    public ModelAndView saveSerija(Serija s, Errors e, Model m, @RequestParam("image") MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        if(e.hasErrors())
            return new ModelAndView("redirect:/greska");

        try {
            ss.sacuvajSeriju(s, multipartFile);
            m.addAttribute("poruka", "Serija uspesno dodata");
            return new ModelAndView("redirect:/");
        }catch (ValidationException ve){
            ra.addFlashAttribute("poruka", "Mora se uneti zanr pri dodavanju nove serije");
            return new ModelAndView("redirect:/novaSerija");
        }
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

    @RequestMapping(value="izvestajSerija", method = RequestMethod.POST)
    public void napraviIzvestaj(HttpServletResponse response, @RequestParam("selektovaneSerije") List<Integer> selektovane) throws Exception{
        JasperPrint jasperPrint = ss.izvestaj(selektovane);
        response.setContentType("application/x-download");
        response.addHeader("Content-disposition", "attachment; filename=Serije.pdf");
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
    }
}
