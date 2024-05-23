package com.example.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.task.entity.MPin;

import java.util.Optional;

@Repository
public interface MPinRepository extends JpaRepository<MPin , String> {

    Optional<MPin> findByUsername(String username);

    boolean existsByUsername(String username);
}