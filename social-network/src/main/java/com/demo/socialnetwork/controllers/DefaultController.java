package com.demo.socialnetwork.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.socialnetwork.controllers.models.RegisterModel;
import com.demo.socialnetwork.documents.MongoDepartmentBlogs;
import com.demo.socialnetwork.documents.embedded.MongoBlog;
import com.demo.socialnetwork.entities.Department;
import com.demo.socialnetwork.entities.UserProfile;
import com.demo.socialnetwork.entities.UserSecurity;
import com.demo.socialnetwork.repos.UserProfileRepo;
import com.demo.socialnetwork.services.AdminService;
import com.demo.socialnetwork.services.DepartmentService;
import com.demo.socialnetwork.services.RegisterService;
import com.demo.socialnetwork.services.UserService;

@Controller
public class DefaultController {
	
	@Autowired
	RegisterService registerService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	DepartmentService departmentService;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(WebDataBinder dataBinder) {
	    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
	    dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@RequestMapping("/")
	public String redirectToUserPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "redirect:/users/"+authentication.getName();
	}
	
	@RequestMapping("/users/{username}")
	public String getUserPage(@PathVariable String username, Model model) {
		UserProfile userProfile = userService.getUserProfile(username);
		
		if(userProfile == null) {
			// TODO return user not found page
		}
		else {
			model.addAttribute("user_profile",userProfile);
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getName().equals(username)) {
			model.addAttribute("session_user",userProfile);
		}
		else {
			UserProfile sessionUserProfile = userService.getUserProfile(authentication.getName());
			if(sessionUserProfile != null) {
				model.addAttribute("session_user",sessionUserProfile);
			}
		}
		
		return "user_page";
	}
	
	@RequestMapping("/deps/{depId}")
	public String getDeparmentPage(@PathVariable int depId, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserProfile sessionUserProfile = userService.getUserProfile(authentication.getName());
		
		Department depInfo = departmentService.getDepartmentInfo(depId);
		MongoDepartmentBlogs depBlogs = null;
		
		if(depInfo != null ) {
			depBlogs = departmentService.getDepartmentPosts(depInfo.getMongoId());
		}
		
		if(depInfo == null || depBlogs == null || sessionUserProfile == null) {
			// TODO return user not found page
		}
		else {
			model.addAttribute("page_dep",depInfo);
			
			Collections.reverse(depBlogs.getBlogs());
			
			model.addAttribute("posts",depBlogs.getBlogs());
			model.addAttribute("session_user",sessionUserProfile);
			
			MongoBlog post = new MongoBlog("");
			model.addAttribute("new_post",post);
		}
		
		return "dep_page";
	}
	
	@PostMapping("/deps/{depId}/post_submit")
	public String addDepartmentPost(@PathVariable int depId, @ModelAttribute("new_post") MongoBlog newPost) {
		departmentService.addBlog(depId, newPost);
		return "redirect:/deps/"+depId+"/";
	}
	
	@RequestMapping("/deps/{depId}/members")
	public String getDepartmentMembersPage(@PathVariable int depId, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserProfile sessionUserProfile = userService.getUserProfile(authentication.getName());
		model.addAttribute("session_user",sessionUserProfile);
		
		Department depInfo = departmentService.getDepartmentInfo(depId);
		model.addAttribute("page_dep",depInfo);

		model.addAttribute("members_num",depInfo.getUsers().size());
		
		return "dep_members";
	}
	
	@RequestMapping("/deps/add_members")
	public String getAddDepartmentMembersPage(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserProfile sessionUserProfile = userService.getUserProfile(authentication.getName());
		model.addAttribute("session_user",sessionUserProfile);
		
		List<UserProfile> allUsers = userService.getAllUserProfiles();
		List<UserProfile> availableUsers = allUsers.stream().filter(x -> x.getDep() == null).collect(Collectors.toList());

		model.addAttribute("users",availableUsers);
		
		return "dep_add_members";
	}
	
	@RequestMapping("/deps/{depId}/add_member/{username}")
	public String addMemberToDepartment(@PathVariable int depId, @PathVariable String username, Model model) {
		departmentService.addMember(depId, username);
		return "redirect:/deps/add_members";
	}
	
	@RequestMapping("/deps/{depId}/remove_member/{username}")
	public String deleteMemberFromDepartment(@PathVariable int depId, @PathVariable String username, Model model) {
		departmentService.removeMember(depId, username);
		return "redirect:/deps/"+depId+"/members";
	}
	
	@GetMapping("/dep_edit")
	public String getDepEditPage(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserProfile sessionUserProfile = userService.getUserProfile(authentication.getName());
		model.addAttribute("session_user",sessionUserProfile);
		model.addAttribute("edit_dep",sessionUserProfile.getDep());
		
		return "dep_edit_page";
	}
	
	@PostMapping("/dep_edit_submit")
	public String submitDepEditPage(
			@Valid @ModelAttribute("edit_dep") Department updatedDep,
			BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		    }
		    return "dep_edit_page";
		}
		else {
			departmentService.updateDepartment(updatedDep);
		}
		return "redirect:/deps/"+updatedDep.getId()+"/";
	}
	

	@RequestMapping("/user_delete/{username}")
	public String deleteUserPage(@PathVariable String username) {
		adminService.deleteUserProfile(username);
		return "redirect:/admin_panel";
	}
	
	@RequestMapping("/user_promote/{username}")
	public String promoteUser(@PathVariable String username) {
		adminService.promoteUser(username);
		return "redirect:/admin_panel";
	}
	
	@RequestMapping("/user_demote/{username}")
	public String demoteUser(@PathVariable String username) {
		adminService.demoteUser(username);
		return "redirect:/admin_panel";
	}
	
	@GetMapping("/user_edit")
	public String getUserEditPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserProfile userProfile = userService.getUserProfile(authentication.getName());
		
		if(userProfile == null) {
			// TODO return user not found page
		}
		model.addAttribute("edit_user", userProfile);
		
		return "user_edit_page";
	}
		
	@PostMapping("/user_edit_submit")
	public String submitUserEditPage(
			@Valid @ModelAttribute("edit_user") UserProfile updatedUserProfile,
			BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
		    for (FieldError error : errors ) {
		        System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
		    }
		    return "user_edit_page";
		}
		else {
			userService.updateUserProfile(updatedUserProfile);
		}
		return "redirect:/";
	}
	
	@RequestMapping("/admin_panel")
	public String getAdministrationPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserProfile sessionUserProfile = userService.getUserProfile(authentication.getName());
		if(sessionUserProfile != null) {
			model.addAttribute("session_user",sessionUserProfile);
		}
		
		List<UserProfile> allUsers = userService.getAllUserProfiles();
		List<UserProfile> adminUsers = allUsers.stream().filter(x -> x.isAdmin()).collect(Collectors.toList());
		List<UserProfile> managerUsers = allUsers.stream().filter(x -> x.isManager()).collect(Collectors.toList());
		List<UserProfile> employeeUsers = allUsers.stream().filter(x -> (!x.isManager() && !x.isAdmin())).collect(Collectors.toList());
		
		model.addAttribute("adminsNum",adminUsers.size());
		model.addAttribute("managersNum",managerUsers.size());
		model.addAttribute("employeesNum",employeeUsers.size());
		
		model.addAttribute("admins",adminUsers);
		model.addAttribute("managers",managerUsers);
		model.addAttribute("employees",employeeUsers);
		
		return "admin_panel";
	}
	
	@RequestMapping("/access_denied")
	public String getAccessDeniedPage() {
		return "access_denied";
	}
	
	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}
	
	@GetMapping("/register")
	public String getRegisterPage(Model model) {
		
		RegisterModel registerModel = new RegisterModel();
		model.addAttribute("new_user", registerModel);
		
		return "register";
	}
	
	@PostMapping("/register_submit")
	public String submitRegister(
			@Valid @ModelAttribute("new_user") RegisterModel registerModel,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

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
