package kg.asiamotors.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.asiamotors.demo.dto.TransmissionDTO;
import kg.asiamotors.demo.service.TransmissionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transmissions")
@Tag(name = "Transmission")
public class TransmissionApiController {
    private final TransmissionService transmissionService;

    public TransmissionApiController(TransmissionService transmissionService) {
        this.transmissionService = transmissionService;
    }

    @GetMapping
    @Operation(summary = "Получение списка трансмиссии")
    public List<TransmissionDTO> getAllTransmissions() {
        return transmissionService.getAllTransmissions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransmissionDTO> getTransmissionById(@PathVariable int id) {
        TransmissionDTO transmissionDTO = transmissionService.getTransmissionById(id);
        return ResponseEntity.ok(transmissionDTO);
    }


    @PostMapping
    @Operation(summary = "Создать трансмиссию")
    public ResponseEntity<TransmissionDTO> createTransmission(@RequestBody TransmissionDTO transmissionDTO) {
        return transmissionService.createTransmission(transmissionDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить трансмиссию")
    public ResponseEntity<TransmissionDTO> updateTransmission(@PathVariable int id, @RequestBody TransmissionDTO transmissionDTO) {
        return transmissionService.updateTransmission(id, transmissionDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить трансмиссию")
    public ResponseEntity<Void> deleteTransmission(@PathVariable int id) {
        return transmissionService.deleteTransmission(id);
    }
    @GetMapping("/search")
    @Operation(summary = "Найти трансмиссию по имени")
    public List<TransmissionDTO> searchTransmissionsByName(@RequestParam String name) {
        return transmissionService.searchTransmissionsByName(name);
    }
    @GetMapping("page")
    @Operation(summary = "Получить трансмиссию с пагинацией")
    public ResponseEntity<Page<TransmissionDTO>> getAllTransmissionDto(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                                       @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(transmissionService.getAllTransmissionDto(offset, limit));
    }
}