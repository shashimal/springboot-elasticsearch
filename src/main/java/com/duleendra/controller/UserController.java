package com.duleendra.controller;

import com.duleendra.exception.AppServiceException;
import com.duleendra.model.SearchQuery;
import com.duleendra.model.User;
import com.duleendra.service.UserService;
import com.duleendra.util.UniqueIDUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


/**
 * User related requests are handled here
 *
 * @author Duleendra Shashimal
 */

@Controller
public class UserController extends AbstractController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    //Message properties
    @Value("${message.success.record.save}")
    private String successSave;
    @Value("${message.success.record.delete}")
    private String successDeletion;
    @Value("${message.failure.record}")
    private String failure;
    @Value("${message.validation.errors}")
    private String validationErrors;
    @Value("${message.object.notfound}")
    private String objectNotFound;

    //User service interface
    @Autowired
    private UserService userService;

    /**
     * Display list of users
     */
    @RequestMapping({ "/", "/users" })
    public String viewUsers(@PageableDefault(value = 50) Pageable pageable, Model model) throws AppServiceException {
        logger.info("Get list of user objects from Elasticsearch");
        Page<User> pageUser = userService.getUsers(pageable);
        model.addAttribute("searchQuery", new SearchQuery());
        model.addAttribute("pageUser", pageUser);
        return "user/list";
    }

    /**
     * Search user information
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String search(@ModelAttribute SearchQuery query, Model model) {
        logger.info("Search user objects in Elasticsearch");
        Page<User> pageUser = userService.search(query.getQuery());
        model.addAttribute("searchQuery", query);
        model.addAttribute("pageUser", pageUser);
        return "user/list";
    }

    /**
     * Display new user creation page
     */
    @RequestMapping(value = "/users/create")
    public String createUser(Model model) {
        logger.info("Display the user creation form");
        model.addAttribute("user", new User());
        return "user/create";
    }

    /**
     * Edit an user object
     *
     * @Parm user id
     */
    @RequestMapping(value = "/users/edit/{id}")
    public String viewUser(@PathVariable String id, Model model) {
        if (id != null) {
            User user = userService.findUserById(id);
            model.addAttribute("user", user);
        } else {
            logger.info("User object id is missing from the request");
            model.addAttribute("errorMessage", objectNotFound);
        }
        return "user/edit";
    }

    /**
     * Save an user object
     */
    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws Exception {
        //If there are any validation errors user will be redirected to the same page
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors in the form");
            model.addAttribute("user", user);
            model.addAttribute("errorMessage", validationErrors);
            return "user/create";
        }

        if (user.getId() == null) {
            user.setId(UniqueIDUtil.getUniqueId());
        }


        //Set the the auto suggestion field values
        String[] suggestions = {user.getFirstName() + " " + user.getLastName()};
        Completion completion = new Completion(suggestions);
        user.setSearchSuggest(completion);

        User savedUser = userService.save(user);

        //If the user object was not saved successfully
        if (savedUser == null) {
            logger.info("User object is null");
            model.addAttribute("errorMessage", failure);
            return "user/create";
        }

        //The user object was saved successfully
        logger.info("User object was saved successfully");
        redirectAttributes.addFlashAttribute("successMessage", successSave);
        return "redirect:/users/";
    }

    /**
     * Delete an user object
     *
     * @Parm user id
     */
    @RequestMapping(value = "/users/delete/{id}")
    public String deleteUser(@PathVariable String id, RedirectAttributes redirectAttr) {
        if (id != null) {
            User user = userService.findUserById(id);
            if (user != null) {
                userService.delete(user);
                logger.info("User object deleted successfully");
                redirectAttr.addFlashAttribute("successMessage", successDeletion);
            } else {
                logger.info("User object is null");
                redirectAttr.addFlashAttribute("errorMessage", failure);
            }
        }
        return "redirect:/users";
    }
}
