package com.foodexpress.food_delivery_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

@PropertySource("classpath:secrets.properties")
@Component
public class JwtTokenValidator extends OncePerRequestFilter {
    private final String SECRET_KEY;


    public JwtTokenValidator(@Value("${JWT_SECRET_KEY}") String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Bearer <token-here>
        String jwt = request.getHeader("Authorization");


        if (jwt != null){
          //Token starts after 7 characters
          jwt = jwt.substring(7);
          try{
              SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
              Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
              String email = String.valueOf(claims.get("email"));
              String authorities = String.valueOf(claims.get("authorities"));

              List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
              System.out.println(auth);
              Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auth);
              SecurityContextHolder.getContext().setAuthentication(authentication);
          }
          catch (Exception e){
              throw new BadCredentialsException("Invalid token");
          }
        }
        filterChain.doFilter(request, response);

    }
}
