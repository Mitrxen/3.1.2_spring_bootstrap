package ru.javamentor.spring_boot.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.javamentor.spring_boot.model.User;
import ru.javamentor.spring_boot.repository.RoleRepository;
import ru.javamentor.spring_boot.service.UserService;

@Controller
public class MainController {

	private final UserService userService;
	private final RoleRepository roleService;

	public MainController(UserService userService, RoleRepository roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}
	
	

	@GetMapping(value = "/")
    public String redirectFromRootToLoginPage() {
        return "redirect:/login";
    }
    
	@GetMapping(value = "/login")
	public String loginPage() {
		return "/login";
	}
	
    @GetMapping(value = "/admin")
    public String adminPanelPage(ModelMap model) {
		model.addAttribute("users", userService.findAllUsers());
		return "/admin";
    }

    @GetMapping(value = "/user")
	public String userProfilePage(ModelMap model) {
		return "/user";
	}
    
    
    @PostMapping(value = "/admin/users")
	public String addNewUser(@ModelAttribute User user, HttpServletRequest request) {
    	    	
    	SortedSet<String> roles = new TreeSet<String>();
		switch (request.getParameter("add_input_userRoles")) {
			case "ADMIN": roles.add("ROLE_ADMIN");
			default: roles.add("ROLE_USER");
		}
		
		userService.saveUser(user, roles);
		return "redirect:/admin";
	}
    
    
    @PatchMapping(value = "/admin/users")
	public String editUser(@ModelAttribute User user, HttpServletRequest request) {

		SortedSet<String> roles = new TreeSet<String>();
		switch (request.getParameter("edit_input_userRoles")) {
			case "ADMIN": roles.add("ROLE_ADMIN");
			default: roles.add("ROLE_USER");
		}
		
		userService.saveUser(user, roles);
		return "redirect:/admin";
	}
    
    
    
	@DeleteMapping("/admin/users/{id}")
	public String deleteAdminUsers(@PathVariable int id) {
		userService.deleteUserById(id);
		return "redirect:/admin";
	}

 
}
