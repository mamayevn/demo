package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.TransmissionDTO;
import kg.asiamotors.demo.services.TransmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transmissions")
public class TransmissionApiController {
    private final TransmissionService transmissionService;

    public TransmissionApiController(TransmissionService transmissionService) {
        this.transmissionService = transmissionService;
    }

    @GetMapping
    public List<TransmissionDTO> getAllTransmissions() {
        return transmissionService.getAllTransmissions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransmissionDTO> getTransmissionById(@PathVariable int id) {
        return transmissionService.getTransmissionById(id);
    }

    @PostMapping
    public ResponseEntity<TransmissionDTO> createTransmission(@RequestBody TransmissionDTO transmissionDTO) {
        return transmissionService.createTransmission(transmissionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransmissionDTO> updateTransmission(@PathVariable int id, @RequestBody TransmissionDTO transmissionDTO) {
        return transmissionService.updateTransmission(id, transmissionDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransmission(@PathVariable int id) {
        return transmissionService.deleteTransmission(id);
    }
}
