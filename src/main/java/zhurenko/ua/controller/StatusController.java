package zhurenko.ua.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/status")
@Controller
public class StatusController {

    @RequestMapping("/200")
    public ResponseEntity<String> status200() {
        return new ResponseEntity<>(HttpStatus.OK);
//        model.addAttribute("status", "200");
//        return "error";
    }

    @RequestMapping("/401")
    public String status401(Model model) {
        //return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        model.addAttribute("status", HttpStatus.UNAUTHORIZED);
        model.addAttribute("msg", "Unauthorized Error, pleas enter your login and password");
        return "error";
    }

    @RequestMapping("/403")
    public String status403(Model model) {
        model.addAttribute("status", HttpStatus.FORBIDDEN);
        model.addAttribute("msg", "Forbidden");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null) {
            System.out.println("403 null");
        }
        else {
            System.out.println("403 " + SecurityContextHolder.getContext().getAuthentication().toString());
            SecurityContextHolder.clearContext();
            System.out.println("403 null null");
        }
        //return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return "error";
    }

}
