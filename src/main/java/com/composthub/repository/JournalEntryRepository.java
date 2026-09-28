package com.composthub.repository;
import com.composthub.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {}