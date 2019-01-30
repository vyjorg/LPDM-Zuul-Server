
package com.lpdm.zuulserver.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpdm.zuulserver.beans.AppUserBean;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("Entering attemptAuthentication");
        logger.info("request: " + request.getQueryString());
        logger.info("response: " + response.getHeaderNames());

        AppUserBean appUserBean = null;
        try {
            appUserBean = new ObjectMapper().readValue(request.getInputStream(), AppUserBean.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("*************");
        logger.info("username :" + appUserBean.getEmail());
        logger.info("password :" + appUserBean.getPassword());
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUserBean.getEmail(), appUserBean.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        logger.info("Entering succesfulAuthentication");

        super.successfulAuthentication(request, response, chain, authResult);
        User springUser = (User)authResult.getPrincipal();
        logger.info("spinguser: " + springUser.toString());
        String jwtToken = Jwts.builder()
                .setSubject(springUser.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET)
                .claim("roles", springUser.getAuthorities())
                .compact();
        logger.info("JWT token: " + jwtToken);
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX +jwtToken);
    }
}
