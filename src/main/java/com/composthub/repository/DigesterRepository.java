package com.composthub.repository;
import com.composthub.model.Digester;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DigesterRepository extends JpaRepository<Digester, Long> {}