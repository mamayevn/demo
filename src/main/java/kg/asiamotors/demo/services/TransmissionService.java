package kg.asiamotors.demo.services;

import kg.asiamotors.demo.models.Brand;
import kg.asiamotors.demo.models.Transmission;
import kg.asiamotors.demo.repasitories.TransmissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransmissionService {
    private final TransmissionRepository transmissionRepository;

    public TransmissionService(TransmissionRepository transmissionRepository) {
        this.transmissionRepository = transmissionRepository;
    }
    public void save(Transmission transmission) {
        transmissionRepository.save(transmission);
    }

    public List<Transmission> findAll() {
        return transmissionRepository.findAll();
    }
    public Transmission findById(int id) {
        return transmissionRepository.findById(id).orElse(null);
    }
}
