package com.dfcr.workshopmanager.repository;

import com.dfcr.workshopmanager.entity.Appointment;
import com.dfcr.workshopmanager.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByVehicleId(Long vehicleId);

    List<Appointment> findByCustomerId(Long customerId);

    List<Appointment> findByScheduledAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
