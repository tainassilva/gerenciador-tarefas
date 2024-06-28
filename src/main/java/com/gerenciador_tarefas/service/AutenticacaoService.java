package com.gerenciador_tarefas.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class AutenticacaoService {

    private static final String BEARER = "Bearer";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String JWT_KEY = "signinKey";
    private static final String AUTHORITIES = "authorities";
    private static final int EXPIRATION_TOKEN_ONE_HOUR = 3600000;

    static public void addJWTToken(HttpServletResponse response, Authentication authentication){

        //Recuperando as permissões que o usuário tinha(TOKEN)
        Map<String, Object> claims = new HashMap<>();

        claims.put(AUTHORITIES, authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        // Quando efetua login gera o JWTToken
        String jwtToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN_ONE_HOUR))
                .signWith(SignatureAlgorithm.HS512, JWT_KEY)
                .addClaims(claims)
                .compact();

        // Gera uma resposta após efetuar login
        response.addHeader(HEADER_AUTHORIZATION, BEARER +" " + jwtToken);
        response.addHeader("Access-Control-Expose-Headers", HEADER_AUTHORIZATION);
    }

    static public Authentication obterAutenticacao(HttpServletRequest request){

        // Recuperar informações para usuário autenticado(TOKEN)
        String token = request.getHeader(HEADER_AUTHORIZATION);

        if (token != null){

            // Recupera usuários
            Claims user = Jwts.parser()
                    .setSigningKey(JWT_KEY)
                    .parseClaimsJws(token.replace(BEARER + " ", ""))
                    .getBody();

            if (user != null) {

                List<SimpleGrantedAuthority> permissoes = ((ArrayList<String>)user.get(AUTHORITIES))
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                // Passando as permissões do usuário para a classe UsernamePasswordAuthenticationToken
                return new UsernamePasswordAuthenticationToken(user, null, permissoes);
            } else {
                throw new RuntimeException("Autenticação falhou");
            }
        }
        return null;
    }
}
