package com.dfcr.workshopmanager.controller;

import com.dfcr.workshopmanager.entity.Part;
import com.dfcr.workshopmanager.service.PartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parts")
public class PartController {

    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Part createPart(@RequestBody @Valid Part part) {
        return partService.createPart(part);
    }

    @GetMapping
    public List<Part> getAllParts() {
        return partService.getAllParts();
    }

    @GetMapping("/{id}")
    public Part getPartById(@PathVariable Long id) {
        return partService.getPartById(id);
    }

    @GetMapping("/reference/{reference}")
    public Part getPartByReference(@PathVariable String reference) {
        return partService.getPartByReference(reference);
    }

    @GetMapping("/name/{name}")
    public List<Part> getPartByName(@PathVariable String name) {
        return partService.getPartByName(name);
    }

    @PutMapping("/{id}")
    public Part updatePart(@PathVariable Long id, @Valid @RequestBody Part part) {
        return partService.updatePart(id, part);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePart(@PathVariable Long id) {
        partService.deletePart(id);
    }


}
