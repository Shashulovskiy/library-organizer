package ru.shashulovskiy.libraryorganizer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.shashulovskiy.libraryorganizer.domain.Reader;
import ru.shashulovskiy.libraryorganizer.service.BookService;
import ru.shashulovskiy.libraryorganizer.service.ReaderService;

import javax.servlet.http.HttpSession;

@Controller
public class ReadersController extends AbstractPageController {
    private final ReaderService readerService;
    private final BookService bookService;

    public ReadersController(final ReaderService readerService, final BookService bookService) {
        this.readerService = readerService;
        this.bookService = bookService;
    }

    @PostMapping("/readers/add")
    private String addReader(@ModelAttribute Reader reader, HttpSession session) {
        // TODO: Add validator for newHash
        if (isAuthorizedLibrarian(session)) {
            readerService.addReader(reader);
            return "redirect:/controlPanel";
        } else {
            return "redirect:/authControlPanel";
        }
    }

    @GetMapping("/readers/books")
    private String getReadersBooks(@ModelAttribute("id") Long id, HttpSession session, Model model) {
        if (isAuthorizedLibrarian(session)) {
            model.addAttribute("rbooks", readerService.getReadersBooks(id));
            model.addAttribute("librarian", getLibrarian(session));
            model.addAttribute("books", bookService.findAll());
            return "controlPanel";
        } else {
            return "redirect:/authControlPanel";
        }
    }
}
