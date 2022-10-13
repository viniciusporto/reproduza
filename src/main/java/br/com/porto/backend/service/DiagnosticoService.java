package br.com.porto.backend.service;

import br.com.porto.backend.data.entity.Diagnostico;
import br.com.porto.backend.data.entity.User;
import br.com.porto.backend.repositories.DiagnosticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class DiagnosticoService implements FilterableCrudService<Diagnostico> {

    private final DiagnosticoRepository diagnosticoRepository;

    @Autowired
    public DiagnosticoService(DiagnosticoRepository diagnosticoRepository) {
        this.diagnosticoRepository = diagnosticoRepository;
    }

    @Override
    public Page<Diagnostico> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return diagnosticoRepository.findByNomeLikeIgnoreCase(repositoryFilter, pageable);
        } else {
            return find(pageable);
        }
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return diagnosticoRepository.countByNomeLikeIgnoreCase(repositoryFilter);
        } else {
            return count();
        }
    }

    public Page<Diagnostico> find(Pageable pageable) {
        return diagnosticoRepository.findBy(pageable);
    }

    @Override
    public JpaRepository<Diagnostico, Long> getRepository() {
        return diagnosticoRepository;
    }

    @Override
    public Diagnostico createNew(User currentUser) {
        return new Diagnostico();
    }

    @Override
    public Diagnostico save(User currentUser, Diagnostico entity) {
        try {
            return FilterableCrudService.super.save(currentUser, entity);
        } catch (DataIntegrityViolationException e) {
            throw new UserFriendlyDataException(
                    "There is already a product with that name. Please select a unique name for the product.");
        }

    }

}