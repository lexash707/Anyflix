package com.lex.netflixserije.services;

import com.lex.netflixserije.report.ZanrPopularnost;
import com.lex.netflixserije.models.Zanr;
import com.lex.netflixserije.repository.ZanrRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ZanrService {
    @Autowired
    private ZanrRepository zr;

    public List<Zanr> getSve(){return zr.findAll();}

    public Zanr getNaziv(String naziv){return zr.findByNaziv(naziv).orElse(null);}

    public JasperPrint izvestaj(){
        List<ZanrPopularnost> popularnostList = new java.util.ArrayList<>(zr.findAll().stream().map(zanr -> zanr.popularnost()).toList());

        popularnostList.sort((o1, o2) -> Float.compare(o2.getProsecnaOcena(),o1.getProsecnaOcena()));

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(popularnostList);
        try{
            InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:templates/IzvestajZanrovi.jrxml"));
            JasperReport jr = JasperCompileManager.compileReport(is);
            is.close();
            return JasperFillManager.fillReport(jr, null, dataSource);
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }
}
