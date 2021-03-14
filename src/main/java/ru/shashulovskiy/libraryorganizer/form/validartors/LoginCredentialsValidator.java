package ru.shashulovskiy.libraryorganizer.form.validartors;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shashulovskiy.libraryorganizer.form.LoginInfoForm;
import ru.shashulovskiy.libraryorganizer.service.LoginService;

@Component
public class LoginCredentialsValidator implements Validator {
    private final LoginService loginService;

    public LoginCredentialsValidator(final LoginService loginService) {
        this.loginService = loginService;
    }

    public boolean supports(Class<?> clazz) {
        return LoginInfoForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            LoginInfoForm enterForm = (LoginInfoForm) target;
            if (loginService.findReader(enterForm.getLogin()) == null) {
                errors.rejectValue("login", "login.reader-not-found", "Reader not found");
            }
        }
    }
}
