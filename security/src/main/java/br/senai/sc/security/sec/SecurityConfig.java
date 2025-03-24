package br.senai.sc.security.sec;

import br.senai.sc.security.repository.UserRepository;
import br.senai.sc.security.sec.service.UsuarioAutenticacaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;


@Configuration
public class SecurityConfig {

//    @Bean
//    public UserDetailsService users(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.builder().username("admin").password("senha").build());
//        manager.createUser(User.builder().username("user").password("senha").build());
//
//        return manager;
//    }
    @Bean
    public AuthenticationProvider userDetailsService(
            UsuarioAutenticacaoService usuarioAutenticacaoService
    ) {
        DaoAuthenticationProvider ap = new DaoAuthenticationProvider();

        ap.setUserDetailsService(usuarioAutenticacaoService);
        ap.setPasswordEncoder(passwordEncoder());

        return ap;
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
