package com.example.task.repository;

import com.example.task.entity.BioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BioIdRepository extends JpaRepository<BioId, String> {

    Optional<BioId> findByUsername(String username);
}