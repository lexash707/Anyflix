package com.lex.netflixserije.controllers;

import com.lex.netflixserije.services.ZanrService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.OutputStream;

@Controller
@RequestMapping(value = "/zanr")
public class ZanrController {
    @Autowired
    private ZanrService zs;

    @GetMapping(value = "/izvestajZanr")
    public void napraviIzvestaj(HttpServletResponse response) throws Exception{
        JasperPrint jasperPrint = zs.izvestaj();
        response.setContentType("application/x-download");
        response.addHeader("Content-disposition", "attachment; filename=Zanrovi.pdf");
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
    }
}
