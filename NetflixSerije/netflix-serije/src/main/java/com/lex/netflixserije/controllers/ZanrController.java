package com.lex.netflixserije.controllers;

import com.lex.netflixserije.models.Zanr;
import com.lex.netflixserije.services.ZanrService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/genre")
public class ZanrController {
    @Autowired
    private ZanrService zs;

    @GetMapping(value = "/all")
    public List<Zanr> all(){
        return zs.getSve();
    }

    @GetMapping(value = "/report")
    public void napraviIzvestaj(HttpServletResponse response) throws Exception{
        JasperPrint jasperPrint = zs.izvestaj();
        response.setContentType("application/x-download");
        response.addHeader("Content-disposition", "attachment; filename=Zanrovi.pdf");
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
    }
}
