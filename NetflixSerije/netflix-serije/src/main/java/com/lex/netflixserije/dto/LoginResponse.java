package com.lex.netflixserije.dto;

import com.lex.netflixserije.models.Korisnik;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private Korisnik user;
}
