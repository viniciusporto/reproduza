package br.com.porto.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.porto.backend.data.entity.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
}
