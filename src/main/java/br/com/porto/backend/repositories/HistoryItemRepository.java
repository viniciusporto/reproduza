package br.com.porto.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.porto.backend.data.entity.HistoryItem;

public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long> {
}
