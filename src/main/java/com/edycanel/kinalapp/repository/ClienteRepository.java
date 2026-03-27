package com.edycanel.kinalapp.repository;

import com.edycanel.kinalapp.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
}
