package com.example.generator.repository;

import com.example.generator.model.entity.DepartureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeparturesRepository extends JpaRepository<DepartureEntity, UUID> {
}
