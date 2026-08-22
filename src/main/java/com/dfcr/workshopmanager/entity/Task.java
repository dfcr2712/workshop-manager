package com.dfcr.workshopmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String description;

    @NotNull
    @PositiveOrZero
    private BigDecimal laborHours;

    @NotNull
    @PositiveOrZero
    private BigDecimal hourlyRate;

    @PositiveOrZero
    private BigDecimal materialCost;

    @ManyToOne
    @JoinColumn(name = "service_order_id", nullable = false)
    private ServiceOrder serviceOrder;

    public BigDecimal laborCost(){
        return laborHours.multiply(hourlyRate);
    }

    public BigDecimal taskTotal(){
        return laborCost().add(materialCost);
    }

}
