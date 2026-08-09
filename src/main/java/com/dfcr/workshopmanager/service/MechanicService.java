package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.Mechanic;
import com.dfcr.workshopmanager.exception.MechanicNotFoundException;
import com.dfcr.workshopmanager.repository.MechanicRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MechanicService {

    private final MechanicRepository mechanicRepository;

    public MechanicService(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
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

        return mechanicRepository.save(existingMechanic);
    }

    public void deleteMechanic(Long id) {
        mechanicRepository.delete(getMechanicById(id));
    }

    public List<Mechanic> getMechanicByName(String name) {
        return mechanicRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Mechanic> getMechanicBySpeciality(String speciality){
        return mechanicRepository.findBySpecialityContainingIgnoreCase(speciality);
    }

    public List<Mechanic> getMechanicByActive(Boolean active){
        return mechanicRepository.findByActive(active);
    }

}
