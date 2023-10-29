package com.example.generator.repository;

import com.example.generator.model.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitiesRepository extends JpaRepository<CityEntity, Long> {
}
