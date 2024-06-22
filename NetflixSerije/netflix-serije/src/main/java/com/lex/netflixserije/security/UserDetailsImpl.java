package com.lex.netflixserije.security;

import java.util.ArrayList;
import java.util.Collection;

import com.lex.netflixserije.models.Korisnik;
import com.lex.netflixserije.models.TipKorisnika;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Builder
@ToString
public class UserDetailsImpl implements UserDetails {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 private Korisnik user;

	 @Override
	 public Collection<SimpleGrantedAuthority> getAuthorities() {
	    Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+ user.getTipkorisnika().getNaziv()));
	    return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
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
		this.user.setUsername(username);
	}

	public void setPassword(String password) {
		this.user.setPassword(password);
	}

	public TipKorisnika getRoles() {
		return user.getTipkorisnika();
	}

	public void setRoles(TipKorisnika tk) {
		this.user.setTipkorisnika(tk);
	}

}
