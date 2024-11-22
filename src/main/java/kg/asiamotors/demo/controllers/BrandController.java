package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.models.Brand;
import kg.asiamotors.demo.services.BrandService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/add_brand")
    @Secured("ROLE_USER")
    public String showAddBrandForm(Model model) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("brands", brandService.findAll());
        return "add_brand";
    }

    @PostMapping("/add_brand")
    @Secured("ROLE_USER")
    public String saveBrand(Brand brand) {
        brandService.save(brand);
        return "redirect:/";
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/addbrand")
    @Secured("ROLE_USER")
    public String showAllBrand(Model model) {
        model.addAttribute("brand", brandService.findAll());
        return "add_brand";
    }

    @GetMapping("/edit_brand/{id}")
    @Secured("ROLE_USER")
    public String showEditBrandForm(@PathVariable("id") int id, Model model) {

        Brand brand = brandService.findById(id);
        if (brand != null) {
            model.addAttribute("brand", brand);
            return "edit_brand";
        }
        return "redirect:/add_brand";
    }

    @PostMapping("/edit_brand")
    @Secured("ROLE_USER")
    public String saveEditedBrand(@ModelAttribute Brand brand) {
        brandService.save(brand);
        return "redirect:/add_brand";
    }
}