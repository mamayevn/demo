package kg.asiamotors.demo.controllers;
import kg.asiamotors.demo.models.Drive;
import kg.asiamotors.demo.services.DriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/drives")
public class DriveController {

    @Autowired
    private DriveService driveService;

    @GetMapping("/add")
    public String showAddDriveForm(Model model) {
        model.addAttribute("drive", new Drive());
        return "add_drive";
    }

    @PostMapping("/add")
    public String addDrive(@ModelAttribute Drive drive) {
        driveService.save(drive);
        return "redirect:/drives/list";
    }

    @GetMapping("/list")
    public String listDrives(Model model) {
        model.addAttribute("drives", driveService.findAll());
        return "list_drives";
    }
}