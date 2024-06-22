package com.lex.netflixserije.models;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lex.netflixserije.report.ZanrPopularnost;
import jakarta.persistence.*;

/**
 * Entity implementation class for Entity: Zanr
 *
 */
@Entity

public class Zanr implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idZanr;
	private String naziv;
	private static final long serialVersionUID = 1L;

	@ManyToMany(mappedBy = "zanrovi")
	@JsonManagedReference
	private List<Serija> serije;

	public Zanr() {
		super();
	}

	public List<Serija> getSerije() {
		return serije;
	}

	public void setSerije(List<Serija> serije) {
		this.serije = serije;
	}

	public int getIdZanr() {
		return this.idZanr;
	}

	public void setIdZanr(int idZanr) {
		this.idZanr = idZanr;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public ZanrPopularnost popularnost(){
		int br = 0;
		float suma = 0;
		float avg;

		int minibr = 0;
		int minisuma= 0;
		float miniavg;

		System.out.println(naziv);

		for (Serija s: this.serije) {
			System.out.println(s.getNaziv());
				br++;
				minisuma = 0;
				minibr = 0;
				for (Ocena o: s.getOcene()) {
					minibr++;
					minisuma+=o.getOcena();
				}
				System.out.println(minisuma + ", " +  minibr);
				miniavg =  minisuma / (float)minibr;
				System.out.println("mini avg" + miniavg);
				suma += miniavg;
		}

		avg = suma/(float)br;
		System.out.println(avg);
		return new ZanrPopularnost(this.naziv, avg);
	}

}
