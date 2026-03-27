package com.edycanel.kinalapp.service;

import com.edycanel.kinalapp.entity.Usuario;
import com.edycanel.kinalapp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        validarUsuario(usuario);
        if(usuario.getEstado() == 0)
            usuario.setEstado(1);
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorCodigoUsuario(int codigoUsuario) {
        return usuarioRepository.findById(codigoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorEstado(int estado) {
        return usuarioRepository.findAll()
                .stream()
                .filter(usuario -> usuario.getEstado() == estado)
                .toList();
    }

    @Override
    public Usuario actualizar(int codigoUsuario, Usuario usuario) {
        if (!usuarioRepository.existsById(codigoUsuario)){
            throw new RuntimeException("Usuario no se encontro con codigo " + codigoUsuario);
        }

        usuario.setCodigoUsuario(codigoUsuario);
        validarUsuario(usuario);

        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(int codigoUsuario) {
        if(!usuarioRepository.existsById(codigoUsuario)){
            throw new RuntimeException("El usuario no se encontro con codigo " + codigoUsuario);
        }
        usuarioRepository.deleteById(codigoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigoUsuario(int codigoUsuario) {
        return usuarioRepository.existsById(codigoUsuario);
    }

    private void validarUsuario(Usuario usuario){
        if (usuario == null){
            throw new IllegalArgumentException("El usuario no puede ser null");
        }

        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()){
            throw new IllegalArgumentException("El username es obligatorio");
        }

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()){
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("El email es obligatorio");
        }

        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()){
            throw new IllegalArgumentException("El rol es obligatorio");
        }
    }
}