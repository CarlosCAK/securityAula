package br.senai.sc.security.sec;

import br.senai.sc.security.repository.UserRepository;
import br.senai.sc.security.sec.service.UsuarioAutenticacaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    SecurityContextRepository securityContextRepository;

    //    @Bean
//    public UserDetailsService users(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.builder().username("admin").password("senha").build());
//        manager.createUser(User.builder().username("user").password("senha").build());
//
//        return manager;
//    }
    @Bean
    public AuthenticationManager autheticationManager(
            UsuarioAutenticacaoService usuarioAutenticacaoService
    ) {
        DaoAuthenticationProvider ap = new DaoAuthenticationProvider();


        ap.setUserDetailsService(usuarioAutenticacaoService);
        ap.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(ap);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(
                                        HttpMethod.POST, "/api/auth/login", "/api/auth/logout").permitAll()
                                .anyRequest().authenticated()


                );
        http.securityContext
                (config -> {
                    config.securityContextRepository(securityContextRepository());
                });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public SecurityContextRepository securityContextRepository(
    ) {
        if(securityContextRepository == null) {
            securityContextRepository = new HttpSessionSecurityContextRepository();
        }
        return securityContextRepository;
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider(UsuarioAutenticacaoService usuarioAutenticacaoService) {
//
//    }
}
