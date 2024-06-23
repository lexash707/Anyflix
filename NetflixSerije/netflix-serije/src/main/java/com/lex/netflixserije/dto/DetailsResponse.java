package com.lex.netflixserije.dto;

import com.lex.netflixserije.models.Ocena;
import com.lex.netflixserije.models.Serija;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
@Builder
@AllArgsConstructor
public class DetailsResponse {
    public Serija serija;
    public List<Ocena> ocene;
}
