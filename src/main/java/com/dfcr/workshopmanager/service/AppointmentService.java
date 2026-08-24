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
        return appointmentRepository.findByScheduledAtBetween(startDate, endDate);
    }

    public Appointment cancelAppointment(Long appointmentId) {
        Appointment appointment = getAppointmentById(appointmentId);

        appointment.setStatus(AppointmentStatus.CANCELLED);
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long appointmentId, Appointment appointment) {
        Appointment oldAppointment = getAppointmentById(appointmentId);

        oldAppointment.setNotes(appointment.getNotes());
        oldAppointment.setReason(appointment.getReason());
        oldAppointment.setScheduledAt(appointment.getScheduledAt());

        return appointmentRepository.save(oldAppointment);
    }

    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.delete(getAppointmentById(appointmentId));
    }

}
