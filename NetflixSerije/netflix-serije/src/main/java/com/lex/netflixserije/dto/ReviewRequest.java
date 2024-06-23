package com.lex.netflixserije.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReviewRequest {
    public int serija;
    public String komentar;
    public int ocena;
}
