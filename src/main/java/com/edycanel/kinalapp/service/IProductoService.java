package com.edycanel.kinalapp.service;

import com.edycanel.kinalapp.entity.Producto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IProductoService {

    @Transactional(readOnly = true)
    List<Producto> listarTodos();

    Producto guardar(Producto producto);

    Optional<Producto> buscarPorCodigoProducto(int codigoProducto);

    @Transactional(readOnly = true)
    List<Producto> buscarPorEstado(int estado);

    Producto actualizar(int codigoProducto, Producto producto);

    void eliminar(int codigoProducto);

    boolean existePorCodigoProducto(int codigoProducto);
}