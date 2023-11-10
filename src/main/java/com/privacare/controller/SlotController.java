package com.privacare.controller;

import com.privacare.model.dto.request.MultipleSlotsRequestDTO;
import com.privacare.model.dto.request.SlotRequestDTO;
import com.privacare.model.dto.response.SlotResponseDTO;
import com.privacare.service.SlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
}
