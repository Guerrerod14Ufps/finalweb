package com.example.demo.repository;

import com.example.demo.entity.DetallesCompra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallesCompraRepository extends JpaRepository<DetallesCompra, Integer> {

	List<DetallesCompra> findByCompraId(Integer factura);
}
