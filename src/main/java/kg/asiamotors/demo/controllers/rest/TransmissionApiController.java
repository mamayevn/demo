package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.TransmissionDTO;
import kg.asiamotors.demo.services.api.TransmissionApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transmissions")
public class TransmissionApiController {
    private final TransmissionApiService transmissionApiService;

    public TransmissionApiController(TransmissionApiService transmissionApiService) {
        this.transmissionApiService = transmissionApiService;
    }

    @GetMapping
    public List<TransmissionDTO> getAllTransmissions() {
        return transmissionApiService.getAllTransmissions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransmissionDTO> getTransmissionById(@PathVariable int id) {
        return transmissionApiService.getTransmissionById(id);
    }

    @PostMapping
    public ResponseEntity<TransmissionDTO> createTransmission(@RequestBody TransmissionDTO transmissionDTO) {
        return transmissionApiService.createTransmission(transmissionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransmissionDTO> updateTransmission(@PathVariable int id, @RequestBody TransmissionDTO transmissionDTO) {
        return transmissionApiService.updateTransmission(id, transmissionDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransmission(@PathVariable int id) {
        return transmissionApiService.deleteTransmission(id);
    }
}
