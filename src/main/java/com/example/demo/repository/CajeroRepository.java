package com.example.demo.repository;

import com.example.demo.entity.Cajero;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CajeroRepository extends JpaRepository<Cajero, Integer> {

	Optional<Cajero> findByToken(String token);
}
