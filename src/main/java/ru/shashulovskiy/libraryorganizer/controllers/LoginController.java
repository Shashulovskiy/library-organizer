package ru.shashulovskiy.libraryorganizer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.shashulovskiy.libraryorganizer.form.AuthControlPanelForm;
import ru.shashulovskiy.libraryorganizer.form.LoginInfoForm;
import ru.shashulovskiy.libraryorganizer.form.validartors.AuthControlPanelFormValidator;
import ru.shashulovskiy.libraryorganizer.form.validartors.LoginCredentialsValidator;
import ru.shashulovskiy.libraryorganizer.service.LoginService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController extends AbstractPageController {
    private final LoginService loginService;
    private final LoginCredentialsValidator loginCredentialsValidator;
    private final AuthControlPanelFormValidator authControlPanelFormValidator;

    public LoginController(final LoginService loginService, final LoginCredentialsValidator loginCredentialsValidator, final AuthControlPanelFormValidator authControlPanelFormValidator) {
        this.loginService = loginService;
        this.loginCredentialsValidator = loginCredentialsValidator;
        this.authControlPanelFormValidator = authControlPanelFormValidator;
    }

    @InitBinder("login")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(loginCredentialsValidator);
    }

    @InitBinder("authControlPanelForm")
    public void controlPanelAuthInitBinder(WebDataBinder binder) {
        binder.addValidators(authControlPanelFormValidator);
    }

    @GetMapping({"/login", "login"})
    public String login(Model model) {
        model.addAttribute("login", new LoginInfoForm());
        return "login";
    }

    @GetMapping({"/authControlPanel", "authControlPanel"})
    public String authControlPanel(Model model) {
        model.addAttribute("authControlPanelForm", new AuthControlPanelForm());
        return "authControlPanel";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("login") LoginInfoForm loginInfoWrapper,
                        BindingResult bindingResult,
                        HttpSession session,
                        Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Such user does not exist.");
            return "login";
        }

        autorizeReader(session, loginService.findReader(loginInfoWrapper.getLogin()));

        return "redirect:/";
    }

    @PostMapping("/authControlPanel")
    public String authControlPanel(@Valid @ModelAttribute("authControlPanelForm") AuthControlPanelForm authControlPanelForm,
                                   BindingResult bindingResult,
                                   HttpSession session,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Such librarian does not exist.");
            return "authControlPanel";
        }

        authorizeLibrarian(session, loginService.findLibrarian(authControlPanelForm.getLogin()));

        return "redirect:/controlPanel";
    }
}
