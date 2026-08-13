package com.dfcr.workshopmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "task_part")
@Getter
@Setter
@NoArgsConstructor
public class TaskPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "part_id", nullable = false)
    private Part part;

    @Positive
    @Column(nullable = false)
    private BigDecimal quantity;

    @Positive
    @Column(nullable = false)
    private BigDecimal unitPrice;

    public BigDecimal partTotal(){
        return quantity.multiply(unitPrice);
    }
}
