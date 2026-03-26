package com.edycanel.kinalapp.service;

import com.edycanel.kinalapp.entity.Venta;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

import java.util.List;

public interface IVentaService {

    @Transactional(readOnly = true)
    List<Venta> listarTodos();

    Venta guardar(Venta venta);

    Optional<Venta> buscarPorCodigoVenta(int codigoVenta);

    @Transactional(readOnly = true)
    List<Venta> buscarPorEstado(int estado);

    Venta actualizar(int codigoVenta, Venta venta);

    void eliminar(int codigoVenta);

    boolean existePorCodigoVenta(int codigoVenta);
}
