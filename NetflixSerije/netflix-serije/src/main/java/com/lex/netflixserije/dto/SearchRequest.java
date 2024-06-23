package com.lex.netflixserije.dto;

import com.lex.netflixserije.models.Zanr;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class SearchRequest {
    String search;
    int zanr;
}
