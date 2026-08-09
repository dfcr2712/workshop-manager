package com.dfcr.workshopmanager.repository;

import com.dfcr.workshopmanager.entity.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MechanicRepository extends JpaRepository<Mechanic, Long> {

    Mechanic findByNameContainingIgnoreCase(String name);

    // CORRIGIR
    List<Mechanic> findBySpecialityContainingIgnoreCase(String speciality);

    // CORRIGIR
    List<Mechanic> findByActive(Boolean active);
}
