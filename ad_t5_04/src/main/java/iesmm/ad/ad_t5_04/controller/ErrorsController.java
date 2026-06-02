package iesmm.ad.ad_t5_04.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class ErrorsController implements ErrorController {
    @RequestMapping("/error")
    @ResponseBody
    public String handleError(){
        return "error";
    }
}
