package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.models.Sale;
import kg.asiamotors.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

@Controller
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SalesPersonService salesPersonService;

    @Autowired
    private CarModelService carModelService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/add")
    public String showAddSaleForm(Model model) {
        model.addAttribute("sale", new Sale());
        model.addAttribute("customers", customerService.findAllCustomers());
        model.addAttribute("salesPersons", salesPersonService.findAllSalespersons());
        model.addAttribute("carModels", carModelService.findAll());
        return "add_sale";
    }

    @PostMapping("/add")
    public String addSale(
            @ModelAttribute Sale sale,
            @RequestParam("file") MultipartFile file,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "add_sale";
        }

        try {
            if (!file.isEmpty()) {
                // Указываем полный путь для сохранения файла
                String uploadDir = "C:/Users/NoutSpace/Desktop/Java projects/uploads";
                String fileName = file.getOriginalFilename();
                Path path = Paths.get(uploadDir, fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                // Сохраните имя файла в сущности
                sale.setFileName(fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении файла", e);
        }

        saleService.saveSale(sale); // Сохранение продажи с именем файла
        return "redirect:/sales/list";
    }

    @GetMapping("/list")
    public String listSales(Model model) {
        model.addAttribute("sales", saleService.findAllSales());
        return "list_sales";
    }
}