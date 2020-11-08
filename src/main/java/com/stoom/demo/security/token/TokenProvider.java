package com.stoom.demo.security.token;

import com.stoom.demo.configuration.AppProperties;
import com.stoom.demo.enums.Role;
import com.stoom.demo.security.exception.*;
import com.stoom.demo.services.UserDetailsService;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public abstract class TokenProvider {

    protected final String HEADER;

    protected final String TOKEN_TYPE;

    private final AppProperties.Token tokenProperties;

    private final UserDetailsService userDetailsService;


    protected TokenProvider(String header,
                            String tokenType,
                            AppProperties.Token tokenProperties,
                            UserDetailsService userDetailsService) {
        HEADER = header;
        TOKEN_TYPE = tokenType;
        this.tokenProperties = tokenProperties;
        this.userDetailsService = userDetailsService;
    }

    public String resolveToken(HttpServletRequest httpServletRequest) throws TokenException {
        final String token = httpServletRequest.getHeader(HEADER);
        if (token != null && token.startsWith(TOKEN_TYPE + " ")) {
            return token.substring(TOKEN_TYPE.length() + 1);
        }
        throw new TokenException();
    }

    public void validateToken(HttpServletRequest httpServletRequest) throws TokenException {
        final String token = resolveToken(httpServletRequest);
        try {
            Jwts.parser()
                    .setSigningKey(tokenProperties.getSecret())
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException |
                IllegalArgumentException |
                SignatureException |
                MalformedJwtException |
                UnsupportedJwtException e) {
            throw new TokenException();
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(HttpServletRequest httpServletRequest) throws TokenException {
        final String token = resolveToken(httpServletRequest);
        try {
            return (List<String>) Jwts.parser()
                    .setSigningKey(tokenProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody()
                    .get("roles");
        } catch (ExpiredJwtException |
                IllegalArgumentException |
                SignatureException |
                MalformedJwtException |
                UnsupportedJwtException e) {
            throw new TokenException();
        }
    }

    public String getSubject(HttpServletRequest httpServletRequest) throws TokenException {
        final String token = resolveToken(httpServletRequest);
        try {
            return Jwts.parser()
                    .setSigningKey(tokenProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException |
                IllegalArgumentException |
                SignatureException |
                MalformedJwtException |
                UnsupportedJwtException e) {
            throw new TokenException();
        }
    }

    public String createToken(Authentication auth) {
        return Jwts.builder()
                .setSubject(auth.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenProperties.getExpire()))
                .claim("roles", auth.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, tokenProperties.getSecret())
                .compact();
    }

    public void writeTokenToResponse(String token, HttpServletResponse httpServletResponse) {
        httpServletResponse.addHeader(HEADER, TOKEN_TYPE + " " + token);
    }

    public LocalDateTime getExpireTime(String accessToken) throws TokenException {
        try {
            Date expire = Jwts.parser()
                    .setSigningKey(tokenProperties.getSecret())
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .getExpiration();
            return expire.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (ExpiredJwtException |
                IllegalArgumentException |
                SignatureException |
                MalformedJwtException |
                UnsupportedJwtException e) {
            throw new TokenException();
        }
    }

    public Authentication getAuthentication(HttpServletRequest httpServletRequest)
            throws TokenException, UserNotFoundException, IllegalAccessError {
        final String subject = getSubject(httpServletRequest);
        final List<String> roles = getRoles(httpServletRequest);
        try {
            UserDetails userDetails;
            if(!roles.contains(Role.ROLE_CUSTOMER.name())) {
                userDetails = userDetailsService.loadById(subject);
                return new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        userDetails.getPassword(),
                        userDetails.getAuthorities());
            } else {
                throw new IllegalAccessError();
            }
        } catch (UsernameNotFoundException | NumberFormatException e) {
            throw new UserNotFoundException();
        }
    }
}
