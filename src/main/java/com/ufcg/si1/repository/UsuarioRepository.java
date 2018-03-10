package com.ufcg.si1.repository;

import com.ufcg.si1.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
