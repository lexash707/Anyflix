package com.lex.netflixserije.models;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lex.netflixserije.report.SerijaPopularnost;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Serija
 *
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Serija implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idSerije;
	private String naziv;
	private String sinopsis;
	private String slika;
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "serija", fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Ocena> ocene;

	@ManyToMany(mappedBy = "omiljene", fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Korisnik> korisnici;

	@ManyToMany(fetch = FetchType.LAZY)
	@JsonBackReference
	@NotNull
	@NotEmpty
	@JoinTable(name = "zanr_serije", joinColumns = @JoinColumn(name = "idZanr"), inverseJoinColumns = @JoinColumn(name = "idSerije"))
	private List<Zanr> zanrovi;

	public Serija() {
		super();
	}

	public List<Ocena> getOcene() {
		return ocene;
	}

	public void setOcene(List<Ocena> ocene) {
		this.ocene = ocene;
	}

	public List<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(List<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}

	public List<Zanr> getZanrovi() {
		return zanrovi;
	}

	public void setZanrovi(List<Zanr> zanrovi) {
		this.zanrovi = zanrovi;
	}

	public int getIdSerije() {
		return this.idSerije;
	};

	public void setIdSerije(int idSerije) {
		this.idSerije = idSerije;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getSinopsis() {
		return this.sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public String getSlika() {
		return this.slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public SerijaPopularnost popularnost(){
		int brKomentara = ocene.stream().filter(ocena -> (ocena.getKomentar() != null)).toList().size();
		float prosecnaOcena = ocene.stream().filter(ocena -> (ocena.getOcena() != 0)).reduce((float) 0, (aFloat, ocena) -> (aFloat + (float) ocena.getOcena()), Float::sum)/ocene.stream().filter(ocena -> (ocena.getOcena() != 0)).count();
		return SerijaPopularnost.builder().brKomentara(brKomentara).brUOmiljenim(korisnici.size()).naziv(this.naziv).prosecnaOcena(prosecnaOcena).build();
	}

	@Override
	public String toString() {
		return "Serija{" +
				"idSerije=" + idSerije +
				", naziv='" + naziv + '\'' +
				'}';
	}
}
