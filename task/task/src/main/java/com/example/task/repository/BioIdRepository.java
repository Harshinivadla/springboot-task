package com.example.task.repository;

import com.example.task.entity.BioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BioIdRepository extends JpaRepository<BioId, String> {
}
