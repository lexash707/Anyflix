package com.lex.netflixserije.security.filter;

import com.lex.netflixserije.security.CustomUserDetailsService;
import com.lex.netflixserije.security.UserDetailsImpl;
import com.lex.netflixserije.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> maybeToken = jwtUtil.getToken(request);
        if (maybeToken.isEmpty()){
            filterChain.doFilter(request,response);
            return;
        }
        String token = maybeToken.get();
        String username = null;
        try {
            username = jwtUtil.extractUsername(token);
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("X-TOKEN-INVALID","true");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Token has expired");
            return;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!jwtUtil.validateToken(token,userDetails)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("X-TOKEN-INVALID","true");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Token has expired");
            return;
        }
        UsernamePasswordAuthenticationToken authenticate = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authenticate.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticate);


        filterChain.doFilter(request,response);
    }
}
