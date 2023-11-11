package com.privacare.controller;

import com.privacare.model.dto.request.MultipleSlotsRequestDTO;
import com.privacare.model.dto.request.SlotRequestDTO;
import com.privacare.model.dto.response.SlotResponseDTO;
import com.privacare.service.SlotService;
import com.privacare.utilities.exception.ErrorDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/slot")
public class SlotController {

    private final SlotService slotService;

    @PostMapping
    public ResponseEntity<SlotResponseDTO> addSlot(@RequestBody @Valid SlotRequestDTO slotRequestDTO) {
        SlotResponseDTO result = this.slotService.addSlot(slotRequestDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<SlotResponseDTO>> addMultipleSlots(@RequestBody @Valid MultipleSlotsRequestDTO multipleSlotsRequestDTO) {
        List<SlotResponseDTO> result = this.slotService.addMultipleSlots(multipleSlotsRequestDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SlotResponseDTO>> getSlots(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        List<SlotResponseDTO> result = this.slotService.getSlots(startDate, endDate);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{slotId}")
    public ResponseEntity<Object> deleteSlot(@PathVariable UUID slotId) {
        try {
            this.slotService.deleteSlot(slotId);
        } catch (Exception e) {
            ErrorDetails errorDetails = ErrorDetails.builder()
                    .timestamp(LocalDateTime.now())
                    .build();
            if (e instanceof DataIntegrityViolationException)
                errorDetails.setMessages(List.of("Slot with id: " + slotId + " has an appointment connected," +
                        " consider deleting the appointment first"));
            if (e instanceof EmptyResultDataAccessException)
                errorDetails.setMessages(List.of("Slot with id: " + slotId + " not found"));

            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/multiple")
    public ResponseEntity<Object> deleteMultipleSlots(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            this.slotService.deleteMultipleSlots(startDate, endDate);
        } catch (Exception e) {
            ErrorDetails errorDetails = ErrorDetails.builder()
                    .timestamp(LocalDateTime.now())
                    .build();
            if (e instanceof DataIntegrityViolationException)
                errorDetails.setMessages(List.of("Slots within the date range: " + startDate + " - " + endDate
                        + " have some appointments connected, consider deleting the appointments first"));

            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
