package iesmm.ad.ad_t5_04.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/")
    public String viewDashboard() {
        return "dashboard";
    }
}
