package zhurenko.ua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zhurenko.ua.service.OwnerService;

@Controller

public class AdminController {

    private OwnerService ownerService;

    @Autowired
    public void setOwnerService(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //@Secured({"ADMIN","USER"})
    @GetMapping("/admin")
    public String adminListGet(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Admin : " + auth.toString());
        model.addAttribute("allUsers", ownerService.getListOwners());
        return "admin";
    }

    @PostMapping("/admin")
    public String adminListPost(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Admin : " + auth.toString());
        model.addAttribute("allUsers", ownerService.getListOwners());
        return "admin";
    }
    //    @PostMapping("/admin")
//    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
//                              @RequestParam(required = true, defaultValue = "" ) String action,
//                              Model model) {
//        if (action.equals("delete")){
//            userService.deleteUser(userId);
//        }
//        return "redirect:/admin";
//    }

//    @GetMapping("/admin/gt/{userId}")
//    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
//        model.addAttribute("allUsers", userService.usergtList(userId));
//        return "admin";
//    }
}
