package com.lex.netflixserije.services;

import com.lex.netflixserije.report.SerijaPopularnost;
import com.lex.netflixserije.models.Korisnik;
import com.lex.netflixserije.models.Serija;
import com.lex.netflixserije.models.Zanr;
import com.lex.netflixserije.repository.SerijaRepository;
import com.lex.netflixserije.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SerijaService {
    @Autowired
    SerijaRepository sr;

    public List<Serija> getSve(){return sr.findAll();}
    public Serija getId(int id){return sr.getById(id);}

    public void sacuvajSeriju(Serija s, MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        s.setSlika(fileName);
        sr.save(s);
        String uploadDir = "src/main/resources/static/img/";

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    }

    public List<Serija> filter(String search, Zanr zanr){
        List<Serija> sveSerije = sr.findAll();

        if(search!= null && !search.isEmpty()){
           sveSerije = sveSerije.stream().filter(serija -> serija.getNaziv().toLowerCase().contains(search.toLowerCase())).toList();
        }

        if(zanr != null){
            sveSerije = sveSerije.stream().filter(serija -> serija.getZanrovi().contains(zanr)).toList();
        }

        return sveSerije;
    }

    public List<Serija> nadjiOmiljene(Korisnik k){
        return sr.findSerijaByKorisniciContaining(k);
    }

    public JasperPrint izvestaj(List<Integer> ids){
        List<SerijaPopularnost> popularnostList = sr.findSerijasByIdSerijeIn(ids).stream().map(Serija::popularnost).toList();

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(popularnostList);
        try{
            InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:templates/IzvestajSerija.jrxml"));
            JasperReport jr = JasperCompileManager.compileReport(is);
            is.close();
            return JasperFillManager.fillReport(jr, null, dataSource);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
