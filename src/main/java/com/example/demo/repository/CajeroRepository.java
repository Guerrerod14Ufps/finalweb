package com.example.demo.repository;

import com.example.demo.entity.Cajero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CajeroRepository extends JpaRepository<Cajero, Integer> {
}
