package com.dfcr.workshopmanager.repository;

import com.dfcr.workshopmanager.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface PartRepository extends JpaRepository<Part, Long> {

    Optional<Part> findByReference(String reference);

    List<Part> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p from Part p WHERE p.stockQuantity <= p.minimumStock")
    List<Part> findLowStockParts();
}
