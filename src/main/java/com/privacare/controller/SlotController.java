package com.privacare.controller;

import com.privacare.model.dto.request.MultipleSlotsRequestDTO;
import com.privacare.model.dto.request.SlotRequestDTO;
import com.privacare.model.dto.response.SlotResponseDTO;
import com.privacare.service.SlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<SlotResponseDTO>> addMultipleSlots(@RequestBody @Valid MultipleSlotsRequestDTO multipleSlotsRequestDTO) {
        List<SlotResponseDTO> result = this.slotService.addMultipleSlots(multipleSlotsRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<List<SlotResponseDTO>> getSlots(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        List<SlotResponseDTO> result = this.slotService.getSlots(startDate, endDate);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{slotId}")
    public ResponseEntity<Object> deleteSlot(@PathVariable UUID slotId) {
        this.slotService.deleteSlot(slotId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/multiple")
    public ResponseEntity<Object> deleteMultipleSlots(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        this.slotService.deleteMultipleSlots(startDate, endDate);
        return ResponseEntity.noContent().build();
    }
}
