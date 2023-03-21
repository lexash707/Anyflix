package com.lex.netflixserije.models;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import jakarta.persistence.*;

/**
 * Entity implementation class for Entity: TipKorisnika
 *
 */
@Entity

public class TipKorisnika implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTipa;
	private String naziv;
	private static final long serialVersionUID = 1L;
	@OneToMany
	private List<Korisnik> korisnici;

	public TipKorisnika() {
		super();
	}
	
	
	public List<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(List<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}
	
	public int getIdTipa() {
		return this.idTipa;
	}

	public void setIdTipa(int idTipa) {
		this.idTipa = idTipa;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	@Override
	public String toString() {
		return "TipKorisnika{" +
				"idTipa=" + idTipa +
				", naziv='" + naziv + '\'';
	}
}
