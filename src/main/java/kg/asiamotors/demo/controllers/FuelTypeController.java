package kg.asiamotors.demo.controllers;
import kg.asiamotors.demo.models.FuelType;
import kg.asiamotors.demo.services.FuelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fuelTypes")
public class FuelTypeController {

    @Autowired
    private FuelTypeService fuelTypeService;

    @GetMapping("/add")
    public String showAddFuelTypeForm(Model model) {
        model.addAttribute("fuelType", new FuelType());
        return "add_fuel_type";
    }

    @PostMapping("/add")
    public String addFuelType(@ModelAttribute FuelType fuelType) {
        fuelTypeService.save(fuelType);
        return "redirect:/fuelTypes/list";
    }

    @GetMapping("/list")
    public String listFuelTypes(Model model) {
        model.addAttribute("fuelTypes", fuelTypeService.findAll());
        return "list_fuel_types";
    }
}