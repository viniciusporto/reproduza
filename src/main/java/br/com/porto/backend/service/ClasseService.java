package br.com.porto.backend.service;

import br.com.porto.backend.data.entity.Classe;
import br.com.porto.backend.data.entity.User;
import br.com.porto.backend.repositories.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class ClasseService implements FilterableCrudService<Classe> {

    private final ClasseRepository classeRepository;

    @Autowired
    public ClasseService(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    @Override
    public Page<Classe> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return classeRepository.findByNomeLikeIgnoreCase(repositoryFilter, pageable);
        } else {
            return find(pageable);
        }
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return classeRepository.countByNomeLikeIgnoreCase(repositoryFilter);
        } else {
            return count();
        }
    }

    public Page<Classe> find(Pageable pageable) {
        return classeRepository.findBy(pageable);
    }

    @Override
    public JpaRepository<Classe, Long> getRepository() {
        return classeRepository;
    }

    @Override
    public Classe createNew(User currentUser) {
        return new Classe();
    }

    @Override
    public Classe save(User currentUser, Classe entity) {
        try {
            return FilterableCrudService.super.save(currentUser, entity);
        } catch (DataIntegrityViolationException e) {
            throw new UserFriendlyDataException(
                    "There is already a product with that name. Please select a unique name for the product.");
        }

    }

}

