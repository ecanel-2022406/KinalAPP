package com.edycanel.kinalapp.service;

import com.edycanel.kinalapp.entity.Producto;
import com.edycanel.kinalapp.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto guardar(Producto producto) {
        validarProducto(producto);
        if(producto.getEstado() == 0)
            producto.setEstado(1);
        return productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorCodigoProducto(int codigoProducto) {
        return productoRepository.findById(codigoProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorEstado(int estado) {
        return productoRepository.findAll()
                .stream()
                .filter(producto -> producto.getEstado() == estado)
                .toList();
    }

    @Override
    public Producto actualizar(int codigoProducto, Producto producto) {
        if (!productoRepository.existsById(codigoProducto)){
            throw new RuntimeException("Producto no se encontro con codigo " + codigoProducto);
        }

        producto.setCodigoProducto(codigoProducto);
        validarProducto(producto);

        return productoRepository.save(producto);
    }

    @Override
    public void eliminar(int codigoProducto) {
        if(!productoRepository.existsById(codigoProducto)){
            throw new RuntimeException("El producto no se encontro con codigo " + codigoProducto);
        }
        productoRepository.deleteById(codigoProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigoProducto(int codigoProducto) {
        return productoRepository.existsById(codigoProducto);
    }

    private void validarProducto(Producto producto){
        if (producto == null){
            throw new IllegalArgumentException("El producto no puede ser null");
        }

        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }

        if (producto.getPrecio() == null){
            throw new IllegalArgumentException("El precio es obligatorio");
        }

        if (producto.getPrecio().doubleValue() <= 0){
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        if (producto.getStock() < 0){
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
}