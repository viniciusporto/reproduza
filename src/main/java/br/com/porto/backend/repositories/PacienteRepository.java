package br.com.porto.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.porto.backend.data.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	Page<Paciente> findBy(Pageable page);

	Page<Paciente> findByNomeLikeIgnoreCase(String nome, Pageable page);

	int countByNomeLikeIgnoreCase(String nome);

}
