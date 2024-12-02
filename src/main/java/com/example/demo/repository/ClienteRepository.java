package com.example.demo.repository;

import com.example.demo.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query("SELECT c FROM Cliente c WHERE c.documento = :documento AND c.tipoDocumento.nombre = :tipoDocumento")
    Optional<Cliente> findByDocumentoAndTipoDocumento(String documento, String tipoDocumento);
}
