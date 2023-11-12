package com.privacare.service;

import com.privacare.model.dto.request.MultipleSlotsRequestDTO;
import com.privacare.model.dto.request.SlotRequestDTO;
import com.privacare.model.dto.response.SlotResponseDTO;
import com.privacare.model.entity.Slot;
import com.privacare.model.entity.User;
import com.privacare.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        List<Slot> slots = this.slotRepository.findByStartsAtBetween(start,end);

        return slots.stream().map(SlotService::mapSlotToSlotResponse).collect(Collectors.toList());
    }


    private static long calculateSlots(LocalTime start, LocalTime end, Integer slotsInterval) {
        long totalMinutes = Duration.between(start, end).toMinutes();
        return (long) Math.ceil((double) totalMinutes / slotsInterval);
    }

    public static SlotResponseDTO mapSlotToSlotResponse(Slot slot) {
        return SlotResponseDTO.builder()
                .id(slot.getId())
                .doctorId(slot.getDoctor().getId())
                .startsAt(slot.getStartsAt())
                .reserved(slot.getReserved())
                .build();
    }

    public void deleteSlot(UUID slotId) throws EmptyResultDataAccessException {
        this.slotRepository.deleteById(slotId);
    }

    public void deleteMultipleSlots(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.of(0, 0, 0));
        LocalDateTime end = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.of(23, 59, 59));

        List<Slot> slots = this.slotRepository.findByStartsAtBetween(start,end);

        this.slotRepository.deleteAll(slots);
    }
}