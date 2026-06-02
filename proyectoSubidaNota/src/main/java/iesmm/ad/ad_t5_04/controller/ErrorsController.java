package iesmm.ad.ad_t5_04.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorsController implements ErrorController {
    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }
}
