package com.privacare.service;

import com.privacare.model.dto.request.AppointmentRequestDTO;
import com.privacare.model.dto.response.AppointmentResponseDTO;
import com.privacare.model.entity.Appointment;
import com.privacare.model.entity.Slot;
import com.privacare.model.entity.User;
import com.privacare.repository.AppointmentRepository;
import com.privacare.utilities.exception.custom.not_found.AppointmentNotFoundException;
import com.privacare.utilities.exception.custom.SlotAlreadyReservedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final UserService userService;

    private final SlotService slotService;

    @Transactional
    public AppointmentResponseDTO addAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        User creator = this.userService.getUserBy(appointmentRequestDTO.getCreatorId());
        User patient = this.userService.getUserBy(appointmentRequestDTO.getPatientId());
        Slot slot = this.slotService.getSlotForAppointmentCreation(appointmentRequestDTO.getSlotId());

        if (slot.getReserved())
            throw new SlotAlreadyReservedException(appointmentRequestDTO.getSlotId());

        Appointment appointment = Appointment.builder()
                .creator(creator)
                .patient(patient)
                .slot(slot)
                .build();

        this.appointmentRepository.save(appointment);
        slot.setReserved(true);
        slot.setAppointment(appointment);
        this.slotService.saveSlot(slot);

        return mapAppointmentToAppointmentResponse(appointment);
    }

    private static AppointmentResponseDTO mapAppointmentToAppointmentResponse(Appointment appointment) {
        return AppointmentResponseDTO.builder()
                .id(appointment.getId())
                .creatorId(appointment.getCreator().getId())
                .patientId(appointment.getPatient().getId())
                .slotId(appointment.getSlot().getId())
                .build();
    }


    public List<AppointmentResponseDTO> getAppointmentsBy(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.of(0, 0, 0));
        LocalDateTime end = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.of(23, 59, 59));

        List<Appointment> appointments = this.appointmentRepository.findBySlotStartsAtBetween(start, end);

        return appointments.stream().map(AppointmentService::mapAppointmentToAppointmentResponse)
                .collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> getAppointmentsBy(UUID patientId) {
        User patient = this.userService.getUserBy(patientId);
        List<Appointment> appointments = this.appointmentRepository.findByPatientId(patient.getId());

        return appointments.stream().map(AppointmentService::mapAppointmentToAppointmentResponse)
                .collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> getAppointmentsBy(String startDate, String endDate, UUID patientId) {
        User patient = this.userService.getUserBy(patientId);
        LocalDateTime start = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.of(0, 0, 0));
        LocalDateTime end = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.of(23, 59, 59));

        List<Appointment> appointments = this.appointmentRepository.
                findBySlotStartsAtBetweenAndPatientId(start, end, patient.getId());

        return appointments.stream().map(AppointmentService::mapAppointmentToAppointmentResponse)
                .collect(Collectors.toList());
    }

    public Appointment getAppointmentBy(UUID id) {
        return this.appointmentRepository.findById(id).orElseThrow(
                () -> new AppointmentNotFoundException(id));
    }

    @Transactional
    public void deleteAppointment(UUID id) {
        Appointment appointment = this.getAppointmentBy(id);
        appointment.getSlot().setReserved(false); // no need to explicitly save because of the transaction
        this.appointmentRepository.deleteById(id);
    }

    @Transactional
    public void deleteAppointments(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.of(0, 0, 0));
        LocalDateTime end = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.of(23, 59, 59));

        List<Appointment> appointments = this.appointmentRepository.findBySlotStartsAtBetween(start, end);
        appointments.stream().forEach(a -> a.getSlot().setReserved(false));
        this.appointmentRepository.deleteAll(appointments);
    }
}
