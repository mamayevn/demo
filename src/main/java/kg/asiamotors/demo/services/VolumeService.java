package kg.asiamotors.demo.services;

import kg.asiamotors.demo.models.Volume;
import kg.asiamotors.demo.repasitories.VolumeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolumeService {
    private final VolumeRepository volumeRepository;

    public VolumeService(VolumeRepository volumeRepository) {
        this.volumeRepository = volumeRepository;
    }

    public void save(Volume volume) {
        volumeRepository.save(volume);
    }

    public List<Volume> findAll() {
        return volumeRepository.findAll();
    }

    public Volume findById(int id) {
        return volumeRepository.findById(id).orElse(null);
    }
}
