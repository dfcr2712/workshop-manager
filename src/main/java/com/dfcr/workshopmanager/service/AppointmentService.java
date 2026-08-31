package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.Appointment;
import com.dfcr.workshopmanager.entity.Customer;
import com.dfcr.workshopmanager.entity.Vehicle;
import com.dfcr.workshopmanager.enums.AppointmentStatus;
import com.dfcr.workshopmanager.exception.AppointmentNotFoundException;
import com.dfcr.workshopmanager.exception.CustomerNotFoundException;
import com.dfcr.workshopmanager.exception.VehicleNotFoundException;
import com.dfcr.workshopmanager.repository.AppointmentRepository;
import com.dfcr.workshopmanager.repository.CustomerRepository;
import com.dfcr.workshopmanager.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class AppointmentService {

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(CustomerRepository customerRepository, VehicleRepository vehicleRepository, AppointmentRepository appointmentRepository) {
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment createAppointment(Long customerId, Long vehicleId, Appointment appointment) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new VehicleNotFoundException(vehicleId));

        if (!vehicle.getCustomer().getId().equals(customerId)) {
            throw new IllegalArgumentException("Vehicle doesn't belong to this customer.");
        }

        if(appointment.getScheduledAt().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Scheduled date cannot be before now.");
        }

        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setCustomer(customer);
        appointment.setVehicle(vehicle);
        appointment.setId(null);
        return appointmentRepository.save(appointment);
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }

    public List<Appointment> getAppointmentsByVehicleId(Long vehicleId) {
        return appointmentRepository.findByVehicleId(vehicleId);
    }

    public List<Appointment> getAppointmentsByCustomerId(Long customerId) {
        return appointmentRepository.findByCustomerId(customerId);
    }

    public List<Appointment> getAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after the end date.");
        }
        return appointmentRepository.findByScheduledAtBetween(startDate, endDate);
    }

    public Appointment confirmAppointment(Long appointmentId){
        Appointment appointment = getAppointmentById(appointmentId);
        validateStatusTransition(appointment.getStatus(), AppointmentStatus.CONFIRMED);
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        return appointmentRepository.save(appointment);
    }

    public Appointment completeAppointment(Long appointmentId){
        Appointment appointment = getAppointmentById(appointmentId);
        validateStatusTransition(appointment.getStatus(), AppointmentStatus.COMPLETED);
        appointment.setStatus(AppointmentStatus.COMPLETED);
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long appointmentId, Appointment appointment) {
        Appointment oldAppointment = getAppointmentById(appointmentId);
        validateAppointmentIsEditable(oldAppointment);
        if(appointment.getScheduledAt().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Scheduled date cannot be before now.");
        }

        oldAppointment.setNotes(appointment.getNotes());
        oldAppointment.setReason(appointment.getReason());
        oldAppointment.setScheduledAt(appointment.getScheduledAt());

        return appointmentRepository.save(oldAppointment);
    }

    public Appointment cancelAppointment(Long appointmentId) {
        Appointment appointment = getAppointmentById(appointmentId);
        validateStatusTransition(appointment.getStatus(), AppointmentStatus.CANCELLED);

        appointment.setStatus(AppointmentStatus.CANCELLED);
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = getAppointmentById(appointmentId);
        if(appointment.getStatus() != AppointmentStatus.CANCELLED){
            throw new IllegalArgumentException("Only cancelled appointments can be deleted.");
        }
        appointmentRepository.delete(appointment);
    }

    private void validateStatusTransition(AppointmentStatus currentStatus, AppointmentStatus newStatus) {
        if (currentStatus == AppointmentStatus.SCHEDULED && (newStatus == AppointmentStatus.SCHEDULED ||
                 newStatus == AppointmentStatus.COMPLETED)) {
            throw new IllegalArgumentException("Appointment scheduled cannot be completed without confirmation.");
        }
        if (currentStatus == AppointmentStatus.CONFIRMED && (newStatus == AppointmentStatus.CONFIRMED ||
                newStatus == AppointmentStatus.SCHEDULED)) {
            throw new IllegalArgumentException("Appointment confirmed cannot be scheduled again.");
        }
        if (currentStatus == AppointmentStatus.COMPLETED) {
            throw new IllegalArgumentException("Cannot change the status after being completed.");
        }
        if (currentStatus == AppointmentStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot change the status after being cancelled.");
        }
    }

    private void validateAppointmentIsEditable(Appointment appointment){
        if(appointment.getStatus() == AppointmentStatus.COMPLETED ||
        appointment.getStatus() == AppointmentStatus.CANCELLED){
            throw new IllegalArgumentException("Completed or cancelled appointments cannot be edited."
            );
        }
    }
}
