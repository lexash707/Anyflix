package com.lex.netflixserije.security;

import com.lex.netflixserije.models.Korisnik;
import com.lex.netflixserije.repository.KorisnikRepository;
import com.lex.netflixserije.services.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private KorisnikRepository kr;

	@Autowired
	private KorisnikService ks;

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return UserDetailsImpl.builder().user(ks.nadjiKorisnika(username)).build();
		
    }
}
