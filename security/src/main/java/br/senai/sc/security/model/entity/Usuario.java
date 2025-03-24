package br.senai.sc.security.model.entity;

import br.senai.sc.security.sec.model.entity.UsuarioDetails;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    public UsuarioDetails usuarioDetails;



}
