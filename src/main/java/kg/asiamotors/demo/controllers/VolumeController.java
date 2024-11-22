package kg.asiamotors.demo.controllers;
import kg.asiamotors.demo.models.Volume;
import kg.asiamotors.demo.services.VolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/volumes")
public class VolumeController {

    @Autowired
    private VolumeService volumeService;

    @GetMapping("/add")
    public String showAddVolumeForm(Model model) {
        model.addAttribute("volume", new Volume());
        return "add_volume";
    }

    @PostMapping("/add")
    public String addVolume(@ModelAttribute Volume volume) {
        volumeService.save(volume);
        return "redirect:/volumes/list";
    }

    @GetMapping("/list")
    public String listVolumes(Model model) {
        model.addAttribute("volumes", volumeService.findAll());
        return "list_volumes";
    }
}