package com.example.demo.repository;

import com.example.demo.entity.Producto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

	Optional<Producto> findByReferencia(String referencia);
}
