package com.dfcr.workshopmanager.repository;

import com.dfcr.workshopmanager.entity.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MechanicRepository extends JpaRepository<Mechanic, Long> {

    List<Mechanic> findByNameContainingIgnoreCase(String name);
    List<Mechanic> findBySpecialityContainingIgnoreCase(String speciality);
    List<Mechanic> findByActive(Boolean active);
}
