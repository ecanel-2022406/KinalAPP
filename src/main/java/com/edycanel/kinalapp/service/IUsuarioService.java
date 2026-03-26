package com.edycanel.kinalapp.service;

import com.edycanel.kinalapp.entity.Usuario;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    @Transactional(readOnly = true)
    List<Usuario> listarTodos();

    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorCodigoUsuario(int codigoUsuario);

    @Transactional(readOnly = true)
    List<Usuario> buscarPorEstado(int estado);

    Usuario actualizar(int codigoUsuario, Usuario usuario);

    void eliminar(int codigoUsuario);

    boolean existePorCodigoUsuario(int codigoUsuario);
}