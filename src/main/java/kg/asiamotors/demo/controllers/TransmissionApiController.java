package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.dto.TransmissionDTO;
import kg.asiamotors.demo.models.Transmission;
import kg.asiamotors.demo.services.TransmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transmissions")
public class TransmissionApiController {
    private final TransmissionService transmissionService;

    public TransmissionApiController(TransmissionService transmissionService) {
        this.transmissionService = transmissionService;
    }

    @GetMapping
    public List<TransmissionDTO> getAllTransmissions() {
        return transmissionService.findAll().stream()
                .map(transmission -> new TransmissionDTO(
                        transmission.getId(),
                        transmission.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransmissionDTO> getTransmissionById(@PathVariable int id) {
        Transmission transmission = transmissionService.findById(id);
        if (transmission != null) {
            return ResponseEntity.ok(new TransmissionDTO(
                    transmission.getId(),
                    transmission.getName()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TransmissionDTO> createTransmission(@RequestBody TransmissionDTO transmissionDTO) {
        Transmission transmission = new Transmission();
        transmission.setName(transmissionDTO.getName());

        transmissionService.save(transmission);

        return ResponseEntity.ok(new TransmissionDTO(
                transmission.getId(),
                transmission.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransmissionDTO> updateTransmission(@PathVariable int id, @RequestBody TransmissionDTO transmissionDTO) {
        Transmission existingTransmission = transmissionService.findById(id);
        if (existingTransmission != null) {
            existingTransmission.setName(transmissionDTO.getName());
            transmissionService.save(existingTransmission);

            return ResponseEntity.ok(new TransmissionDTO(
                    existingTransmission.getId(),
                    existingTransmission.getName()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransmission(@PathVariable int id) {
        Transmission existingTransmission = transmissionService.findById(id);
        if (existingTransmission != null) {
            transmissionService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
