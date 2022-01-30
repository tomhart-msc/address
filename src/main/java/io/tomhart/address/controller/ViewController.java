package io.tomhart.address.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {
    public static final String LIST_USERS_ROUTE = "list-users";
    public static final String USER_ROUTE = "user";

    @RequestMapping({"/list-users", "/"})
    public String viewUsers(
            Model model,
            @RequestParam(value = "offset", required = false, defaultValue = "0") String offset) {
        model.addAttribute("offset", offset);
        return LIST_USERS_ROUTE;
    }

    @RequestMapping("/user/{id}")
    public String viewUserDetails(Model model, @PathVariable final String id) {
        model.addAttribute("id", id);
        return USER_ROUTE;
    }
}
