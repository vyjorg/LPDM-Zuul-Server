package com.lpdm.zuulserver.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        logger.info("entering doFilterInternal");

        String jwt = request.getHeader(SecurityConstants.HEADER_STRING);
        if(jwt == null || !jwt.startsWith(SecurityConstants.TOKEN_PREFIX)){
            filterChain.doFilter(request,response);
            logger.info("jwt: " + jwt);
            return;
        }
        logger.info("jwt: " + jwt);

        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(jwt.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getBody();

        logger.info("claims: " + claims);

        String username = claims.getSubject();

        logger.info("username: " + username);

        ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");

        logger.info("roles: " + roles.toString());

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        roles.forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.get("authority")));
        });

        UsernamePasswordAuthenticationToken authenticatedUser = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        filterChain.doFilter(request, response);


     }
}
