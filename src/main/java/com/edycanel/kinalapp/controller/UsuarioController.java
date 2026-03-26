package com.edycanel.kinalapp.controller;

import com.edycanel.kinalapp.entity.Usuario;
import com.edycanel.kinalapp.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET listar todos
    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // GET buscar por ID
    @GetMapping("/{codigoUsuario}")
    public ResponseEntity<Usuario> buscarPorCodigoUsuario(@PathVariable int codigoUsuario){
        return usuarioService.buscarPorCodigoUsuario(codigoUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET buscar por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Usuario>> buscarPorEstado(@PathVariable int estado){
        List<Usuario> usuarios = usuarioService.buscarPorEstado(estado);
        if(usuarios.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    // POST crear usuario
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.guardar(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE eliminar
    @DeleteMapping("/{codigoUsuario}")
    public ResponseEntity<Void> eliminar(@PathVariable int codigoUsuario){
        try {
            if (!usuarioService.existePorCodigoUsuario(codigoUsuario)){
                return ResponseEntity.notFound().build();
            }
            usuarioService.eliminar(codigoUsuario);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT actualizar
    @PutMapping("/{codigoUsuario}")
    public ResponseEntity<?> actualizar(@PathVariable int codigoUsuario, @RequestBody Usuario usuario){
        try{
            if(!usuarioService.existePorCodigoUsuario(codigoUsuario)){
                return ResponseEntity.notFound().build();
            }

            Usuario usuarioActualizado = usuarioService.actualizar(codigoUsuario, usuario);
            return ResponseEntity.ok(usuarioActualizado);

        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}