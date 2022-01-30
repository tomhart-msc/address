package io.tomhart.address.controller;

import io.tomhart.address.model.Page;
import io.tomhart.address.model.User;
import io.tomhart.address.model.UserDetails;
import io.tomhart.address.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public Page<User> listUsers(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset) {
        return userService.listUsers(offset);
    }

    @GetMapping("/user/{id}")
    public UserDetails getUser(@PathVariable String id) {
        return userService.getUser(id);
    }
}
