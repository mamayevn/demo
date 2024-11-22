package kg.asiamotors.demo.controllers;
import kg.asiamotors.demo.models.Transmission;
import kg.asiamotors.demo.services.TransmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transmissions")
public class TransmissionController {

    @Autowired
    private TransmissionService transmissionService;

    @GetMapping("/add")
    public String showAddTransmissionForm(Model model) {
        model.addAttribute("transmission", new Transmission());
        return "add_transmission";
    }

    @PostMapping("/add")
    public String addTransmission(@ModelAttribute Transmission transmission) {
        transmissionService.save(transmission);
        return "redirect:/transmissions/list";
    }

    @GetMapping("/list")
    public String listTransmissions(Model model) {
        model.addAttribute("transmissions", transmissionService.findAll());
        return "list_transmissions";
    }
}