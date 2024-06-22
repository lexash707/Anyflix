package com.lex.netflixserije.models;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/**
 * Entity implementation class for Entity: Korisnik
 *
 */
@Entity

public class Korisnik implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idKorisnika;
	private String username;
	private String password;
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name = "idTipa")
	private TipKorisnika tipkorisnika;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JsonBackReference
	@JoinTable(name = "omiljene_serije", joinColumns = @JoinColumn(name = "idSerije"), inverseJoinColumns = @JoinColumn(name = "idKorisnika"))
	private List<Serija> omiljene;

	@OneToMany
	@JsonBackReference
	private List<Ocena> ocene;

	public Korisnik() {
		super();
	}
	
	public List<Serija> getOmiljene() {
		return omiljene;
	}

	public void setOmiljene(List<Serija> omiljene) {
		this.omiljene = omiljene;
	}

	public List<Ocena> getOcene() {
		return ocene;
	}

	public void setOcene(List<Ocena> ocene) {
		this.ocene = ocene;
	}

	public TipKorisnika getTipkorisnika() {
		return tipkorisnika;
	}

	public void setTipkorisnika(TipKorisnika tipkorisnika) {
		this.tipkorisnika = tipkorisnika;
	}

	public int getIdKorisnika() {
		return this.idKorisnika;
	}

	public void setIdKorisnika(int idKorisnika) {
		this.idKorisnika = idKorisnika;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Korisnik{" +
				"idKorisnika=" + idKorisnika +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", tipkorisnika=" + tipkorisnika +
				", omiljene=" + omiljene +
				", ocene=" + ocene +
				'}';
	}
}
