package ru.shashulovskiy.libraryorganizer.form.validartors;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shashulovskiy.libraryorganizer.form.AuthControlPanelForm;
import ru.shashulovskiy.libraryorganizer.service.LoginService;

@Component
public class AuthControlPanelFormValidator implements Validator {
    private final LoginService loginService;

    public AuthControlPanelFormValidator(final LoginService loginService) {
        this.loginService = loginService;
    }

    public boolean supports(Class<?> clazz) {
        return AuthControlPanelForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            AuthControlPanelForm enterForm = (AuthControlPanelForm) target;
            if (loginService.findLibrarian(enterForm.getLogin()) == null) {
                errors.rejectValue("login", "login.librarian-not-found", "Librarian not found");
            }
        }
    }
}
