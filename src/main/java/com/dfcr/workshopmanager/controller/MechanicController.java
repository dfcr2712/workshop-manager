package com.dfcr.workshopmanager.controller;

import com.dfcr.workshopmanager.entity.Mechanic;
import com.dfcr.workshopmanager.service.MechanicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mechanics")
public class MechanicController {

    private final MechanicService mechanicService;

    public MechanicController(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    @PostMapping
    public Mechanic createMechanic(@RequestBody @Valid Mechanic mechanic) {
        return mechanicService.createMechanic(mechanic);
    }

    @GetMapping
    public List<Mechanic> getAllMechanics() {
        return mechanicService.getAllMechanics();
    }

    @GetMapping("/{id}")
    public Mechanic getMechanicById(@PathVariable Long id) {
        return mechanicService.getMechanicById(id);
    }

    @GetMapping("/name/{name}")
    public List<Mechanic> findByName(@PathVariable String name){
        return mechanicService.getMechanicByName(name);
    }

    @GetMapping("/speciality/{speciality}")
    public List<Mechanic> getMechanicBySpeciality(@PathVariable String speciality){
        return mechanicService.getMechanicBySpeciality(speciality);
    }

    @GetMapping("/active/{active}")
    public List<Mechanic> getMechanicByActive(@PathVariable boolean active){
        return mechanicService.getMechanicByActive(active);
    }

    @PutMapping("/{id}")
    public Mechanic updateMechanic(@PathVariable Long id, @RequestBody @Valid Mechanic updateMechanic) {
        return mechanicService.updateMechanic(id, updateMechanic);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMechanic(@PathVariable Long id) {
        mechanicService.deleteMechanic(id);
    }



}
