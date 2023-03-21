package com.lex.netflixserije.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ZanrPopularnost {

    private String nazivZanra;
    private float prosecnaOcena;

}
