package com.lex.netflixserije.security;

import com.lex.netflixserije.models.Korisnik;
import com.lex.netflixserije.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private KorisnikRepository kr;
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Korisnik user = kr.findKorisnikByUsername(username).orElseThrow(RuntimeException::new);
		UserDetailsImpl userDetails =new UserDetailsImpl();
		userDetails.setUsername(user.getUsername());
		userDetails.setPassword(user.getPassword());
		userDetails.setRoles(user.getTipkorisnika());

		System.out.println(userDetails);
		System.out.println(user);
		return userDetails;
		
    }
 
     
 


	
     
}
