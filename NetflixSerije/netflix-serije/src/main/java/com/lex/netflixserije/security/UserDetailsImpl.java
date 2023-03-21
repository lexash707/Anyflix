package com.lex.netflixserije.security;

import java.util.ArrayList;
import java.util.Collection;

import com.lex.netflixserije.models.Korisnik;
import com.lex.netflixserije.models.TipKorisnika;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	 private String password;
	 private TipKorisnika tipKorisnika;

	 @Override
	 public Collection<SimpleGrantedAuthority> getAuthorities() {
	    Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+ tipKorisnika.getNaziv()));
	    return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TipKorisnika getRoles() {
		return tipKorisnika;
	}

	public void setRoles(TipKorisnika tk) {
		this.tipKorisnika = tk;
	}

	@Override
	public String toString() {
		return "UserDetailsImpl{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", tipKorisnika=" + tipKorisnika +
				'}';
	}
}
