package com.edycanel.kinalapp.repository;


import com.edycanel.kinalapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}