package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.Mechanic;
import com.dfcr.workshopmanager.exception.MechanicNotFoundException;
import com.dfcr.workshopmanager.repository.MechanicRepository;
import com.dfcr.workshopmanager.repository.ServiceOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MechanicService {

    private final MechanicRepository mechanicRepository;
    private final ServiceOrderRepository serviceOrderRepository;

    public MechanicService(MechanicRepository mechanicRepository, ServiceOrderRepository serviceOrderRepository) {
        this.mechanicRepository = mechanicRepository;
        this.serviceOrderRepository = serviceOrderRepository;
    }

    public Mechanic createMechanic(Mechanic mechanic) {
        return mechanicRepository.save(mechanic);
    }

    public List<Mechanic> getAllMechanics() {
        return mechanicRepository.findAll();
    }

    public Mechanic getMechanicById(Long id) {
        return mechanicRepository.findById(id).orElseThrow(() -> new MechanicNotFoundException(id));
    }

    public Mechanic updateMechanic(Long id, Mechanic updateMechanic) {
        Mechanic existingMechanic = getMechanicById(id);

        existingMechanic.setName(updateMechanic.getName());
        existingMechanic.setEmail(updateMechanic.getEmail());
        existingMechanic.setPhoneNumber(updateMechanic.getPhoneNumber());
        existingMechanic.setSpeciality(updateMechanic.getSpeciality());
        existingMechanic.setActive(updateMechanic.isActive());

        mechanicRepository.save(existingMechanic);

        return existingMechanic;
    }

    public void deleteMechanic(Long id) {
        mechanicRepository.delete(getMechanicById(id));
    }

    public Mechanic getMechanicByName(String name) {
        return mechanicRepository.findByNameContainingIgnoreCase(name);
    }


}
