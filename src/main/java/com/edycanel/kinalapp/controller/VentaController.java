package com.edycanel.kinalapp.controller;

import com.edycanel.kinalapp.entity.Venta;
import com.edycanel.kinalapp.service.IVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.List;

@RestController

@RequestMapping("/ventas")

public class VentaController {

    private final IVentaService ventaService;

    public VentaController(IVentaService ventaService){
        this.ventaService = ventaService;
    }

    @GetMapping
    public ResponseEntity<List<Venta>> listar(){
        List<Venta> ventas = ventaService.listarTodos();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{codigoVenta}")
    public ResponseEntity<Venta> buscarPorCodigoVenta(@PathVariable int codigoVenta){
        return  ventaService.buscarPorCodigoVenta(codigoVenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Venta>> buscarPorEstado(@PathVariable int estado){
        List<Venta> ventas = ventaService.buscarPorEstado(estado);
        if (ventas.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ventas);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Venta venta){
        try {
            Venta nuevoCodigoVenta = ventaService.guardar(venta);
            return new ResponseEntity<>(nuevoCodigoVenta, HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigoVenta}")
    public  ResponseEntity<Void> eliminar(@PathVariable int codigoVenta){
        try {
            if (!ventaService.existePorCodigoVenta(codigoVenta)){
                return ResponseEntity.notFound().build();
            }
            ventaService.eliminar(codigoVenta);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigoVenta}")
    public ResponseEntity<?> actualizar(@PathVariable int codigoVenta, @RequestBody Venta venta){
        try {
            if (!ventaService.existePorCodigoVenta(codigoVenta)){
                return ResponseEntity.notFound().build();
            }
            Venta ventaActualizado = ventaService.actualizar(codigoVenta, venta);
            return ResponseEntity.ok(ventaActualizado);
        }catch (IllegalArgumentException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}
