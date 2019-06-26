package com.phattai.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security
        .authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 3_600_000; // 1 hour
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    public static void addAuthentication(HttpServletResponse res, String username, List<GrantedAuthority> roles) {
    	System.out.println(roles);
    	
    	String infoUser = username + "&&" + roles.toString();
    	
        String JWT = Jwts.builder()
                .setSubject(infoUser)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String infoUser = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            String[] temp = infoUser.split("&&");
            
            String username = temp[0];
            String role = "ROLE_" + temp[1].substring(1, temp[1].length()-1).toUpperCase();
            
//            
//            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            
            GrantedAuthority grantedAuthority = new GrantedAuthority() {
				@Override
				public String getAuthority() {
					// TODO Auto-generated method stub
					return role;
				}
			};
			
			List<GrantedAuthority> roles = new ArrayList<>();
			roles.add(grantedAuthority);
            
            return username != null ?
                    new UsernamePasswordAuthenticationToken(username, null, roles) :
                    null;
        }
        return null;
    }
}