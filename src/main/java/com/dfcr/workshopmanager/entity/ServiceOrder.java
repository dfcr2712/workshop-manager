package com.dfcr.workshopmanager.entity;

import com.dfcr.workshopmanager.enums.EstimateStatus;
import com.dfcr.workshopmanager.enums.ServiceOrderPriority;
import com.dfcr.workshopmanager.enums.ServiceOrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "serviceOrder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceOrderStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    private Mechanic mechanic;

    @Enumerated(EnumType.STRING)
    private EstimateStatus estimateStatus;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Long mileage;

    @Enumerated(EnumType.STRING)
    private ServiceOrderPriority priority;

    private LocalDateTime startedAt;

    private LocalDateTime expectedCompletionAt;

    private String customerNotes;
    private String internalNotes;
}
