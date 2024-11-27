package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.TransmissionDTO;
import kg.asiamotors.demo.models.Transmission;
import kg.asiamotors.demo.repository.TransmissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransmissionService {
    private final TransmissionRepository transmissionRepository;

    public TransmissionService(TransmissionRepository transmissionRepository) {
        this.transmissionRepository = transmissionRepository;
    }

    public List<TransmissionDTO> getAllTransmissions() {
        return transmissionRepository.findAll().stream()
                .map(transmission -> new TransmissionDTO(
                        transmission.getId(),
                        transmission.getName()))
                .collect(Collectors.toList());
    }

    public ResponseEntity<TransmissionDTO> getTransmissionById(int id) {
        Transmission transmission = findById(id);
        if (transmission != null) {
            return ResponseEntity.ok(new TransmissionDTO(
                    transmission.getId(),
                    transmission.getName()));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<TransmissionDTO> createTransmission(TransmissionDTO transmissionDTO) {
        Transmission transmission = new Transmission();
        transmission.setName(transmissionDTO.getName());

        save(transmission);

        return ResponseEntity.ok(new TransmissionDTO(
                transmission.getId(),
                transmission.getName()));
    }

    public ResponseEntity<TransmissionDTO> updateTransmission(int id, TransmissionDTO transmissionDTO) {
        Transmission existingTransmission = findById(id);
        if (existingTransmission != null) {
            existingTransmission.setName(transmissionDTO.getName());
            save(existingTransmission);

            return ResponseEntity.ok(new TransmissionDTO(
                    existingTransmission.getId(),
                    existingTransmission.getName()));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> deleteTransmission(int id) {
        Transmission existingTransmission = findById(id);
        if (existingTransmission != null) {
            delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private void save(Transmission transmission) {
        transmissionRepository.save(transmission);
    }

    public Transmission findById(int id) {
        return transmissionRepository.findById(id).orElse(null);
    }

    private void delete(int id) {
        transmissionRepository.deleteById(id);
    }
    public List<TransmissionDTO> searchTransmissionsByName(String name) {
        return transmissionRepository.findByName(name)
                .stream()
                .map(transmission -> new TransmissionDTO(
                        transmission.getId(),
                        transmission.getName()))
                .collect(Collectors.toList());
    }
    public Page<TransmissionDTO> getAllTransmissionDto(int offset, int limit){
        Pageable pageable = PageRequest.of(offset, limit);
        return transmissionRepository.findAllTransmissionDtos(pageable);
    }
}
