package com.dfcr.workshopmanager.controller;

import com.dfcr.workshopmanager.entity.Appointment;
import com.dfcr.workshopmanager.enums.AppointmentStatus;
import com.dfcr.workshopmanager.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/customer/{customerId}/vehicle/{vehicleId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment createAppointment(@PathVariable Long customerId,
                                         @PathVariable Long vehicleId,
                                         @Valid @RequestBody Appointment appointment){

        return appointmentService.createAppointment(customerId, vehicleId, appointment);
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id){
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping
    public List<Appointment> getAllAppointments(){
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/status/{status}")
    public List<Appointment> getAppointmentsByStatus(@PathVariable AppointmentStatus status){
        return appointmentService.getAppointmentsByStatus(status);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public List<Appointment> getAppointmentsByVehicleId(@PathVariable Long vehicleId){
        return appointmentService.getAppointmentsByVehicleId(vehicleId);
    }

    @GetMapping("/customer/{customerId}")
    public List<Appointment> getAppointmentsByCustomerId(@PathVariable Long customerId){
        return appointmentService.getAppointmentsByCustomerId(customerId);
    }

    @GetMapping("/date-range")
    public List<Appointment> getAppointmentsByDateRange(@RequestParam LocalDateTime startDate,
                                                        @RequestParam LocalDateTime endDate){
        return appointmentService.getAppointmentsByDateRange(startDate, endDate);
    }

    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @Valid @RequestBody Appointment appointment){
        return appointmentService.updateAppointment(id, appointment);
    }

    @PutMapping("/{id}/cancel")
    public Appointment cancelAppointment(@PathVariable Long id){
        return appointmentService.cancelAppointment(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAppointment(@PathVariable Long id){
        appointmentService.deleteAppointment(id);
    }



}
