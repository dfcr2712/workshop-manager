package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.Part;
import com.dfcr.workshopmanager.exception.PartNotFoundException;
import com.dfcr.workshopmanager.repository.PartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PartService {
    private final PartRepository partRepository;

    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    public Part createPart(Part part) {
        return partRepository.save(part);
    }

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    public Part getPartById(Long id) {
        return partRepository.findById(id).orElseThrow(() -> new PartNotFoundException("Part with id " + id + " not found."));
    }

    public Part getPartByReference(String reference) {
        return partRepository.findByReference(reference).orElseThrow(() -> new PartNotFoundException("Part with reference " + reference + " not found."));
    }

    public List<Part> getPartByName(String name) {
        List<Part> parts = partRepository.findByNameContainingIgnoreCase(name);

        if (parts.isEmpty()) {
            throw new PartNotFoundException("Part with name " + name + " not found.");
        }
        return parts;
    }

    public void deletePart(Long partId) {
        Part part = getPartById(partId);
        partRepository.delete(part);
    }

    public Part updatePart(Long partId, Part updatePart) {
        Part existingPart = getPartById(partId);

        existingPart.setName(updatePart.getName());
        existingPart.setReference(updatePart.getReference());
        existingPart.setStockQuantity(updatePart.getStockQuantity());
        existingPart.setUnitPrice(updatePart.getUnitPrice());
        existingPart.setActive(updatePart.isActive());
        existingPart.setMinimumStock(updatePart.getMinimumStock());

        return partRepository.save(existingPart);
    }

    public Part addStock(Long id, BigDecimal quantity) {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        Part foundPart = partRepository.findById(id).
                orElseThrow(() -> new PartNotFoundException("Part with id " + id + " not found."));

        BigDecimal newStock = foundPart.getStockQuantity().add(quantity);
        foundPart.setStockQuantity(newStock);
        return partRepository.save(foundPart);
    }

    public List<Part> findLowStockParts(){
        return partRepository.findLowStockParts();
    }
}
