package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.models.Brand;
import kg.asiamotors.demo.services.BrandService;
import kg.asiamotors.demo.services.CarService;
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
    public String showAddBrandForm(Model model) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("brands", brandService.findAll());
        return "add_brand"; // Отображаем шаблон add_brand.html
    }

    @PostMapping("/add_brand")
    public String saveBrand(Brand brand) {
        brandService.save(brand);
        return "redirect:/main";
    }

    @GetMapping("/addbrand")
    public String showAllBrand(Model model) {
        // Получаем список машин или других объектов
        model.addAttribute("brand", brandService.findAll());  // Список всех машин
        return "add_brand"; //
    }
    @GetMapping("/edit_brand/{id}")
    public String showEditBrandForm(@PathVariable("id") int id, Model model) {
        // Получаем бренд по id
        Brand brand = brandService.findById(id);
        if (brand != null) {
            model.addAttribute("brand", brand);
            return "edit_brand"; // Шаблон для редактирования
        }
        return "redirect:/add_brand"; // Если бренд не найден, редиректим на главную страницу
    }
    // Метод для сохранения изменений
    @PostMapping("/edit_brand")
    public String saveEditedBrand(@ModelAttribute Brand brand) {
        brandService.save(brand); // Сохраняем обновленный бренд
        return "redirect:/add_brand"; // Перенаправляем обратно на страницу добавления бренда
    }
}
