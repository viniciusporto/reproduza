package br.com.porto.backend.service;

import java.util.Optional;

import br.com.porto.backend.data.entity.Dominio;
import br.com.porto.backend.data.entity.Paciente;
import br.com.porto.backend.repositories.DominioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.porto.backend.data.entity.User;
import br.com.porto.backend.repositories.PacienteRepository;

@Service
public class DominioService implements FilterableCrudService<Dominio> {

    private final DominioRepository dominioRepository;

    @Autowired
    public DominioService(DominioRepository dominioRepository) {
        this.dominioRepository = dominioRepository;
    }

    @Override
    public Page<Dominio> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return dominioRepository.findByNomeLikeIgnoreCase(repositoryFilter, pageable);
        } else {
            return find(pageable);
        }
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return dominioRepository.countByNomeLikeIgnoreCase(repositoryFilter);
        } else {
            return count();
        }
    }

    public Page<Dominio> find(Pageable pageable) {
        return dominioRepository.findBy(pageable);
    }

    @Override
    public JpaRepository<Dominio, Long> getRepository() {
        return dominioRepository;
    }

    @Override
    public Dominio createNew(User currentUser) {
        return new Dominio();
    }

    @Override
    public Dominio save(User currentUser, Dominio entity) {
        try {
            return FilterableCrudService.super.save(currentUser, entity);
        } catch (DataIntegrityViolationException e) {
            throw new UserFriendlyDataException(
                    "There is already a product with that name. Please select a unique name for the product.");
        }

    }

}

