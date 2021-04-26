package com.demo.socialnetwork.controllers;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.socialnetwork.controllers.models.RegisterModel;
import com.demo.socialnetwork.entities.UserSecurity;
import com.demo.socialnetwork.services.RegisterService;

@Controller
public class DefaultController {
	
	@Autowired
	RegisterService registerService;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(WebDataBinder dataBinder) {
	    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
	    dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@RequestMapping("/")
	public String getStartingPage() {
		return "index";
	}
	
	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}
	
	@GetMapping("/register")
	public String getRegisterPage(Model model) {
		
		RegisterModel registerModel = new RegisterModel();
		registerModel.getUserSecurity().setEnabled(true);
		model.addAttribute("new_user", registerModel);
		
		return "register";
	}
	
	@PostMapping("/register_submit")
	public String submitRegister(
			@Valid @ModelAttribute("new_user") RegisterModel registerModel,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		System.out.println("tuta");
		if(bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		    }
		    return "register";
		}
		else {
			int error_state = registerService.registerUser(registerModel);
			if(error_state == 1) {
				model.addAttribute("error", "1");
				return "register";
			}
		}
		redirectAttributes.addAttribute("success", "1");
		return "redirect:/login";
	}
}
