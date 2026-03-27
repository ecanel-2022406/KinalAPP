package com.edycanel.kinalapp.service;

import com.edycanel.kinalapp.entity.Venta;
import com.edycanel.kinalapp.repository.VentaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService implements IVentaService{
    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository)  {
        this.ventaRepository = ventaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarTodos(){
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardar(Venta venta){
        validarVenta(venta);
        if (venta.getEstado() == 0)
            venta.setEstado(1);
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorCodigoVenta(int codigoVenta){
        return ventaRepository.findById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorEstado(int estado){
        return ventaRepository.findAll()
                .stream()
                .filter(cliente -> cliente.getEstado() == estado)
                .toList();
    }

    @Override
    public  Venta actualizar(int codigoVenta, Venta venta){
        if (!ventaRepository.existsById(codigoVenta)){
            throw new RuntimeException("Venta no se encontro con codigoVenta "+ codigoVenta);
        }

        venta.setCodigoVenta(codigoVenta);
        validarVenta(venta);

        return ventaRepository.save(venta);
    }

    @Override
    public void eliminar(int codigoVenta){
        if (!ventaRepository.existsById(codigoVenta)){
            throw new RuntimeException("La venta no se encontro con codigoVenta " + codigoVenta);
        }
        ventaRepository.deleteById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigoVenta(int codigoVenta){
        return ventaRepository.existsById(codigoVenta);
    }

    private void validarVenta(Venta venta){

        if (venta == null){
            throw new IllegalArgumentException("La venta no puede ser null");
        }

        if (venta.getFechaVenta() == null){
            throw new IllegalArgumentException("La fecha de la venta es obligatoria");
        }

        if (venta.getTotal() == null){
            throw new IllegalArgumentException("El total es obligatorio");
        }

        if (venta.getTotal().doubleValue() <= 0){
            throw new IllegalArgumentException("El total debe ser mayor a 0");
        }
    }
}
