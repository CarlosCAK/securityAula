package br.senai.sc.security.sec.repository;

import br.senai.sc.security.sec.model.entity.UsuarioDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioDetailsRepository extends JpaRepository<UsuarioDetails, Long> {

    Optional<UsuarioDetails> findByUsername(String username);

}
