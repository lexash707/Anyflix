package com.lex.netflixserije.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SerijaPopularnost {

    private String naziv;
    private int brKomentara;
    private float prosecnaOcena;
    private int brUOmiljenim;
}
