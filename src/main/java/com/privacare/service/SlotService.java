package com.privacare.service;

import com.privacare.model.dto.request.MultipleSlotsRequestDTO;
import com.privacare.model.dto.request.SlotRequestDTO;
import com.privacare.model.dto.response.SlotResponseDTO;
import com.privacare.model.entity.Slot;
import com.privacare.model.entity.User;
import com.privacare.repository.SlotRepository;
import com.privacare.utilities.exception.custom.SlotHasAppointmentConnectedException;
import com.privacare.utilities.exception.custom.not_found.SlotNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlotService {

    private final SlotRepository slotRepository;

    private final UserService userService;

    public SlotResponseDTO addSlot(SlotRequestDTO slotRequestDTO) {
        User doctor = this.userService.getUserBy(slotRequestDTO.getDoctorId());

        Slot slot = Slot.builder()
                .doctor(doctor)
                .reserved(false)
                .appointment(null)
                .startsAt(slotRequestDTO.getStartsAt())
                .build();

        this.slotRepository.save(slot);
        return mapSlotToSlotResponse(slot);
    }

    @Transactional
    public List<SlotResponseDTO> addMultipleSlots(MultipleSlotsRequestDTO m) {
        User doctor = this.userService.getUserBy(m.getDoctorId());

        long numberOfSlots = calculateSlots(m.getStartTime(), m.getEndTime(), m.getSlotsInterval());

        List<SlotResponseDTO> slotsResponse = new ArrayList<>();

        for (LocalDate date = m.getStartDate(); !date.isAfter(m.getEndDate()); date = date.plusDays(1)) {
            if (!m.getSelectedDays().contains(date.getDayOfWeek()))
                continue;

            for (long i = 0; i < numberOfSlots; i++) {
                Slot slot = Slot.builder()
                        .doctor(doctor)
                        .reserved(false)
                        .appointment(null)
                        .startsAt(date.atTime(
                                m.getStartTime()
                                        .plusMinutes(i * m.getSlotsInterval())))
                        .build();

                this.slotRepository.save(slot);
                slotsResponse.add(mapSlotToSlotResponse(slot));
            }
        }

        return slotsResponse;
    }

    public List<SlotResponseDTO> getSlots(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.of(0, 0, 0));
        LocalDateTime end = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.of(23, 59, 59));

        List<Slot> slots = this.slotRepository.findByStartsAtBetweenOrderByStartsAtAsc(start, end);

        return slots.stream().map(SlotService::mapSlotToSlotResponse).collect(Collectors.toList());
    }

    public Slot getSlotBy(UUID id) {
        return this.slotRepository.findById(id).orElseThrow(
                () -> new SlotNotFoundException(id));
    }

    public Slot getSlotForAppointmentCreation(UUID id) {
        return this.slotRepository.findByIdForAppointment(id).orElseThrow(
                () -> new SlotNotFoundException(id));
    }


    private static long calculateSlots(LocalTime start, LocalTime end, Integer slotsInterval) {
        if(slotsInterval<=0)
            throw new ArithmeticException(slotsInterval.toString());
        long totalMinutes = Duration.between(start, end).toMinutes();
        return (long) Math.ceil((double) totalMinutes / slotsInterval);
    }

    public void deleteSlot(UUID slotId) throws EmptyResultDataAccessException {
        try {
            this.slotRepository.deleteById(slotId);
        } catch (Exception e) {
            if (e instanceof EmptyResultDataAccessException)
                throw new SlotNotFoundException(slotId);
            if (e instanceof DataIntegrityViolationException)
                throw new SlotHasAppointmentConnectedException(slotId);
        }
    }

    public void deleteMultipleSlots(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.of(0, 0, 0));
        LocalDateTime end = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.of(23, 59, 59));

        List<Slot> slots = this.slotRepository.findByStartsAtBetweenOrderByStartsAtAsc(start, end);

        try {
            this.slotRepository.deleteAll(slots);
        } catch (DataIntegrityViolationException e) {
            throw new SlotHasAppointmentConnectedException(List.of(start, end));
        }
    }

    public void saveSlot(Slot slot) {
        this.slotRepository.save(slot);
    }

    public static SlotResponseDTO mapSlotToSlotResponse(Slot slot) {
        return SlotResponseDTO.builder()
                .id(slot.getId())
                .doctorId(slot.getDoctor().getId())
                .startsAt(slot.getStartsAt())
                .reserved(slot.getReserved())
                .build();
    }

    public SlotResponseDTO getSlotResponseBy(UUID slotId) {
        return mapSlotToSlotResponse(this.slotRepository.findById(slotId).orElseThrow(
                () -> new SlotNotFoundException(slotId)));
    }
}
