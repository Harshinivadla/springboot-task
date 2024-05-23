package com.example.task.repository;

import com.example.task.entity.AlbumClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumClientRepository extends JpaRepository<AlbumClient, Integer> {
}
