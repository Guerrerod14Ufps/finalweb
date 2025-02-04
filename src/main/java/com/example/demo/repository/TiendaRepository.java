package com.example.demo.repository;


import com.example.demo.entity.Tienda;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TiendaRepository extends JpaRepository<Tienda, Integer> {

	Optional<Tienda> findByUuid(String uuid);
}
