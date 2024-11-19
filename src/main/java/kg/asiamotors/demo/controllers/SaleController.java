package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.models.Sale;
import kg.asiamotors.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
        model.addAttribute("today", LocalDate.now());
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

        if (sale.getSaleDate() == null) {
            sale.setSaleDate(LocalDate.now());
        }

        try {
            if (!file.isEmpty()) {
                String uploadDir = "C:/Users/NoutSpace/Desktop/Java projects/uploads";
                String fileName = file.getOriginalFilename();
                Path path = Paths.get(uploadDir, fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                sale.setFileName(fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении файла", e);
        }

        saleService.saveSale(sale);
        return "redirect:/sales/list";
    }
    @GetMapping("/list")
    public String listSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            Model model) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<Sale> salesPage = saleService.findAllSales(pageable);

        model.addAttribute("sales", salesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", salesPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);

        return "list_sales";
    }
}
