package com.example.task.repository;

import com.example.task.entity.MPin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MPinRepository extends JpaRepository<MPin , String> {
}
