package com.dfcr.workshopmanager.repository;

import com.dfcr.workshopmanager.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartRepository extends JpaRepository<Part, Long> {

    Optional<Part> findByReference(String reference);
    List<Part> findByNameContainingIgnoreCase(String name);
}
