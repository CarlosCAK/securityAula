package br.senai.sc.security.sec.controller;

import br.senai.sc.security.sec.model.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AutenticacaoController {

    private AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository;


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
  
    public void  login(@RequestBody LoginDto login,
                         HttpServletRequest request,
                         HttpServletResponse response) {

        Authentication auth =
                new UsernamePasswordAuthenticationToken(login.username(), login.password());

        auth = authenticationManager.authenticate(auth);

        if(auth.isAuthenticated()){
            SecurityContext securityContext =
                    SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(auth);
            securityContextRepository.saveContext(securityContext, request, response);

            response.setStatus(HttpStatus.OK.value());
            return;
//
//            var usuarioAutenticado =
//                    SecurityContextHolder.getContext().getAuthentication();
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

    }




}
