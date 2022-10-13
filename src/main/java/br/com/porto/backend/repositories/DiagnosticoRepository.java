package br.com.porto.backend.repositories;

import br.com.porto.backend.data.entity.Diagnostico;
import br.com.porto.backend.data.entity.Dominio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {

    Page<Diagnostico> findBy(Pageable page);

    Page<Diagnostico> findByNomeLikeIgnoreCase(String nome, Pageable page);

    int countByNomeLikeIgnoreCase(String nome);

}
