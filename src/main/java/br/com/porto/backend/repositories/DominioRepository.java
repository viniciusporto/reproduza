package br.com.porto.backend.repositories;

import br.com.porto.backend.data.entity.Dominio;
import br.com.porto.backend.data.entity.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DominioRepository extends JpaRepository<Dominio, Long> {

    Page<Dominio> findBy(Pageable page);

    Page<Dominio> findByNomeLikeIgnoreCase(String nome, Pageable page);

    int countByNomeLikeIgnoreCase(String nome);

}

