package com.realestate.estate.repositories;

import com.realestate.estate.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// Magic: This automatically creates CRUD endpoints (GET /properties, POST /properties, etc.)
@RepositoryRestResource 
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
