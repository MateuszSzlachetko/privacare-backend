package com.privacare.controller;

import com.privacare.model.dto.request.AppointmentRequestDTO;
import com.privacare.model.dto.response.AppointmentResponseDTO;
import com.privacare.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> addAppointment(@RequestBody @Valid AppointmentRequestDTO appointmentRequestDTO) {
        AppointmentResponseDTO result = this.appointmentService.addAppointment(appointmentRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(params = {"startDate", "endDate", "patientId"})
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsBy(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam UUID patientId) {
        List<AppointmentResponseDTO> result = this.appointmentService.getAppointmentsBy(startDate, endDate, patientId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(params = {"startDate", "endDate"})
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        List<AppointmentResponseDTO> result = this.appointmentService.getAppointmentsBy(startDate, endDate);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(params = {"patientId"})
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsById(
            @RequestParam UUID patientId) {
        List<AppointmentResponseDTO> result = this.appointmentService.getAppointmentsBy(patientId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(params = {"slotId"})
    public ResponseEntity<AppointmentResponseDTO> getAppointmentsBySlotId(
            @RequestParam UUID slotId) {
        AppointmentResponseDTO result = this.appointmentService.getAppointmentBySlotId(slotId);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAppointment(@PathVariable UUID id) {
        this.appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/multiple")
    public ResponseEntity<Object> deleteMultipleSlots(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        this.appointmentService.deleteAppointments(startDate, endDate);
        return ResponseEntity.noContent().build();
    }
}
