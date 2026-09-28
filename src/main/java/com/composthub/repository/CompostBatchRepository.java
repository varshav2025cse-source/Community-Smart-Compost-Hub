package com.composthub.repository;
import com.composthub.model.CompostBatch;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CompostBatchRepository extends JpaRepository<CompostBatch, Long> {}