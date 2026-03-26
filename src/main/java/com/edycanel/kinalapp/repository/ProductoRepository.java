package com.edycanel.kinalapp.repository;

import com.edycanel.kinalapp.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
