package zhurenko.ua.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zhurenko.ua.model.Owner;
import zhurenko.ua.service.BookService;
import zhurenko.ua.service.OwnerService;

@Controller
public class SecurityController {

    private final OwnerService ownerService;
    private final BookService bookService;

    public SecurityController(OwnerService ownerService, BookService bookService) {
        this.ownerService = ownerService;
        this.bookService = bookService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(name = "error", required = false) Boolean error,
                        Model model){
        if(Boolean.TRUE.equals(error)){
            model.addAttribute("error" , true);
        }
        return "signin";
    }

    @GetMapping("/registration")
    public String registrationNew(){
        return"signup";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute Owner owner){
        ownerService.saveOwner(owner);
        return "redirect:/login";
    }

//    @PostMapping("/logout")
//    public String logout(
//                         Model model){
//            model.addAttribute("books", bookService.getAllBooks());
//
//        return "showBooks";
//    }
//    @GetMapping("/role{user}")
//    public String role(@PathVariable User user){
//
//    }
}
