package br.com.porto.backend.service;

import java.util.Optional;

import br.com.porto.backend.data.entity.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.porto.backend.data.entity.User;
import br.com.porto.backend.repositories.PacienteRepository;

@Service
public class PacienteService implements FilterableCrudService<Paciente> {

	private final PacienteRepository pacienteRepository;

	@Autowired
	public PacienteService(PacienteRepository pacienteRepository) {
		this.pacienteRepository = pacienteRepository;
	}

	@Override
	public Page<Paciente> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return pacienteRepository.findByNomeLikeIgnoreCase(repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return pacienteRepository.countByNomeLikeIgnoreCase(repositoryFilter);
		} else {
			return count();
		}
	}

	public Page<Paciente> find(Pageable pageable) {
		return pacienteRepository.findBy(pageable);
	}

	@Override
	public JpaRepository<Paciente, Long> getRepository() {
		return pacienteRepository;
	}

	@Override
	public Paciente createNew(User currentUser) {
		return new Paciente();
	}

	@Override
	public Paciente save(User currentUser, Paciente entity) {
		try {
			return FilterableCrudService.super.save(currentUser, entity);
		} catch (DataIntegrityViolationException e) {
			throw new UserFriendlyDataException(
					"There is already a product with that name. Please select a unique name for the product.");
		}

	}

}
