package com.gerenciador_tarefas.config;

import com.gerenciador_tarefas.filter.AutenticacaoFiltro;
import com.gerenciador_tarefas.filter.LoginFiltro;
import com.gerenciador_tarefas.permissoes.PermissaoEnum;
import com.gerenciador_tarefas.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {


        http.csrf(crsf -> crsf.disable())
                // Mapeando as rotas HTTPs
                .authorizeHttpRequests( auth -> {
                    // Todo mundo pode acessar a rota de login
                    auth.requestMatchers("/login").permitAll()
                            // Se chamar o teste-api , vamos dizer que todos podem acessar sem login
                            .requestMatchers(HttpMethod.GET, "/teste-api").permitAll()
                            // Podemos exigir que apenas o administrador entre nessa rota
                            .requestMatchers(HttpMethod.GET, "/teste-api-bem-vindo").hasAuthority(PermissaoEnum.ADMINISTRADOR.toString())
                            // Podemos exigir que para recuperar usuários, apenas o usuário pode fazer isso
                            .requestMatchers(HttpMethod.GET, "/usuarios").hasAuthority(PermissaoEnum.USUARIO.toString())
                            // Quem pode criar usuários é apenas um administrador
                            .requestMatchers(HttpMethod.POST, "/usuarios").hasAuthority(PermissaoEnum.ADMINISTRADOR.toString())
                            // Para entrar no /gerenciador-tarefas e fazer um POST precisa de autorização do administrador
                            .requestMatchers(HttpMethod.POST, "/gerenciador-tarefas").hasAuthority(PermissaoEnum.ADMINISTRADOR.toString())
                            .anyRequest()
                            .authenticated();
            });

        // Quando acessar o /login a gente vai adicionar uma configuração
        http.addFilterBefore(new LoginFiltro("/login", authenticationConfiguration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new AutenticacaoFiltro(), UsernamePasswordAuthenticationFilter.class);

       return http.build();
    }
}