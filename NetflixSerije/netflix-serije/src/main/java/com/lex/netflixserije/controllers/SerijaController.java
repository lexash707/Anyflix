package com.lex.netflixserije.controllers;

import com.lex.netflixserije.dto.DetailsResponse;
import com.lex.netflixserije.dto.FavRequest;
import com.lex.netflixserije.dto.ReviewRequest;
import com.lex.netflixserije.models.Korisnik;
import com.lex.netflixserije.models.Ocena;
import com.lex.netflixserije.models.Serija;
import com.lex.netflixserije.models.Zanr;
import com.lex.netflixserije.repository.SerijaRepository;
import com.lex.netflixserije.repository.ZanrRepository;
import com.lex.netflixserije.services.KorisnikService;
import com.lex.netflixserije.services.OcenaService;
import com.lex.netflixserije.services.SerijaService;
import com.lex.netflixserije.utils.JwtUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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

    @Autowired
    private JwtUtil ju;

    @Autowired
    private SerijaRepository sr;

    @Autowired
    private ZanrRepository zr;

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Serija> getSve(){
        return ss.getSve();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<HttpServletResponse> saveSerija(@RequestParam("naziv") String naziv,
                                                          @RequestParam("image") MultipartFile multipartFile,
                                                          @RequestParam("sinopsis") String sinopsis,
                                                          @RequestParam("zanrovi") String zanrovi,
                                                          @RequestParam("slika") String imeSlike) throws IOException {

        List<Integer> zanrIds = stringConverter(zanrovi);

        List<Zanr> zanrSerije = zanrIds.stream().map(
                integer -> zr.findById(integer).orElseThrow()
        ).toList();

        Serija s = Serija.builder()
                .naziv(naziv)
                .sinopsis(sinopsis)
                .slika(imeSlike)
                .zanrovi(zanrSerije)
                .build();
        System.out.println("hello it me + " + s);
        try {
            ss.sacuvajSeriju(s, multipartFile);
            System.out.println("ovde se zavrsava save + " + s);
        }catch (ValidationException ve){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        System.out.println("ce ga returnujemo");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/details/{id}")
    public DetailsResponse detalji(@PathVariable int id){
        Serija s = ss.getId(id);
        List<Ocena> ocene = s.getOcene();
        return new DetailsResponse(s, ocene);
    }

    @RequestMapping(value = "/add-favourite", method = RequestMethod.POST)
    public ResponseEntity<HttpServletResponse> dodajOmiljenu(HttpServletRequest request, @RequestBody FavRequest fr){
        Korisnik k = ks.nadjiKorisnika(ju.extractUsername(ju.getToken(request).orElseThrow()));
        Serija s = sr.findById(fr.getIdSerije()).orElseThrow();
        ks.dodajOmiljenu(k, s);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public ResponseEntity<HttpServletResponse> oceniSeriju(HttpServletRequest request, @RequestBody ReviewRequest reviewRequest){
        String username = ju.extractUsername(ju.getToken(request).orElse(""));
        Ocena o = Ocena.builder()
                .korisnik(ks.nadjiKorisnika(username))
                .ocena(reviewRequest.ocena)
                .komentar(reviewRequest.komentar)
                .serija(sr.findById(reviewRequest.serija).orElseThrow())
                .build();
        os.sacuvajOcenu(o);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value="/report", method = RequestMethod.POST)
    public void napraviIzvestaj(HttpServletResponse response, @RequestParam(value = "selektovaneSerije", required = false) List<Integer> selektovane) throws Exception{
        JasperPrint jasperPrint = ss.izvestaj(selektovane);
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "attachment; filename=Serije.pdf");
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
    }

    private List<Integer> stringConverter(String str){
        String cleanedInput = str.replaceAll("\\[|\\]|\\s", "");

        // Split by commas
        String[] strArray = cleanedInput.split(",");

        // Convert to integers
        List<Integer> intList = new ArrayList<>();
        for (String s : strArray) {
            try {
                intList.add(Integer.parseInt(s.trim()));
            } catch (NumberFormatException e) {
                System.err.println("Invalid integer format: " + s);
            }
        }

        return intList;
    }
}
