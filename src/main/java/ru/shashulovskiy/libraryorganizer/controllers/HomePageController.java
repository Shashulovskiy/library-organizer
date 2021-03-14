package ru.shashulovskiy.libraryorganizer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.shashulovskiy.libraryorganizer.service.BookClaimService;
import ru.shashulovskiy.libraryorganizer.service.BookService;
import ru.shashulovskiy.libraryorganizer.service.ReaderService;

import javax.servlet.http.HttpSession;

@Controller
public class HomePageController extends AbstractPageController {

    private final BookService bookService;
    private final ReaderService readerService;
    private final BookClaimService bookClaimService;

    public HomePageController(final BookService bookService, final ReaderService readerService, final BookClaimService bookClaimService) {
        this.bookService = bookService;
        this.readerService = readerService;
        this.bookClaimService = bookClaimService;
    }

    @GetMapping({"", "/"})
    public String getHomePage(HttpSession session, Model model) {
        if (isAutorizedReader(session)) {
            model.addAttribute("reader", readerService.findById(getReader(session).getId()));
            model.addAttribute("availableBooks", bookService.finaAllAvailable());
            return "home";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping({"controlPanel", "/controlPanel"})
    public String getControlPanel(HttpSession session, Model model) {
        if (isAuthorizedLibrarian(session)) {
            model.addAttribute("librarian", getLibrarian(session));
            model.addAttribute("books", bookService.findAll());
            model.addAttribute("claims", bookClaimService.findAll());
            model.addAttribute("rbooks", "");
            model.addAttribute("rhash", "");
            return "controlPanel";
        } else {
            return "redirect:/authControlPanel";
        }
    }
}
