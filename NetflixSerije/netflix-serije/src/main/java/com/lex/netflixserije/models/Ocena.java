package com.lex.netflixserije.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Entity implementation class for Entity: Ocena
 *
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ocena implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idOcene;
	private int ocena;
	private String komentar;
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name = "idKorisnika")
	private Korisnik korisnik;

	@ManyToOne
	@JoinColumn(name = "idSerije")
	@JsonBackReference
	private Serija serija;

	public String getKomentar() {
		return komentar;
	}

	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}

	public Serija getSerija() {
		return serija;
	}

	public void setSerija(Serija serija) {
		this.serija = serija;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public int getIdOcene() {
		return this.idOcene;
	}

	public void setIdOcene(int idOcene) {
		this.idOcene = idOcene;
	}

	public int getOcena() {
		return this.ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

}
