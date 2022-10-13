package br.com.porto.backend.repositories;

import br.com.porto.backend.data.entity.Classe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClasseRepository extends JpaRepository<Classe, Long> {

    Page<Classe> findBy(Pageable page);

    Page<Classe> findByNomeLikeIgnoreCase(String nome, Pageable page);

    int countByNomeLikeIgnoreCase(String nome);

}