package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.models.CarModel;
import kg.asiamotors.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/models")
public class CarModelController {
    @Autowired
    private CarModelService carModelService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private VolumeService volumeService;

    @Autowired
    private TransmissionService transmissionService;

    @Autowired
    private DriveService driveService;

    @Autowired
    private FuelTypeService fuelTypeService;

    @GetMapping("/add_model")
    public String showAddModelForm(Model model) {
        model.addAttribute("carModel", new CarModel());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("volumes", volumeService.findAll());
        model.addAttribute("transmissions", transmissionService.findAll());
        model.addAttribute("drives", driveService.findAll());
        model.addAttribute("fuelTypes", fuelTypeService.findAll());
        return "add_model";
    }

    @PostMapping("/add_model")
    public String addModel(@ModelAttribute CarModel carModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add_model";
        }
        carModelService.save(carModel);
        return "redirect:/models/model_list";
    }

    @GetMapping("/model_list")
    public String listModels(Model model) {
        model.addAttribute("models", carModelService.findAll());
        return "model_list";
    }
}