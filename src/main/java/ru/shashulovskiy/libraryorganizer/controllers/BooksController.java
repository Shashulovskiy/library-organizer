package ru.shashulovskiy.libraryorganizer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.shashulovskiy.libraryorganizer.domain.Book;
import ru.shashulovskiy.libraryorganizer.form.UpdateBookHashForm;
import ru.shashulovskiy.libraryorganizer.service.BookService;
import ru.shashulovskiy.libraryorganizer.service.LibrarianService;

import javax.servlet.http.HttpSession;

@Controller
public class BooksController extends AbstractPageController {
    private final BookService bookService;
    private final LibrarianService librarianService;

    public BooksController(final BookService bookService1, final LibrarianService librarianService) {
        this.bookService = bookService1;
        this.librarianService = librarianService;
    }

    @PostMapping("/books/remove")
    public String removeBook(@ModelAttribute("id") Long id, HttpSession session) {
        if (isAuthorizedLibrarian(session)) {
            bookService.removeBook(id);
            return "redirect:/controlPanel";
        } else {
            return "redirect:/authControlPanel";
        }
    }

    @PostMapping("/books/updateHash")
    public String updateBookHash(@ModelAttribute UpdateBookHashForm updateBookHashForm, HttpSession session) {
        // TODO: Add validator for newHash
        if (isAuthorizedLibrarian(session)) {
            bookService.updateBookHash(updateBookHashForm.getId(), updateBookHashForm.getNewHash());
            return "redirect:/controlPanel";
        } else {
            return "redirect:/authControlPanel";
        }
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book, HttpSession session) {
        // TODO: Add validator for book
        if (isAuthorizedLibrarian(session)) {
            bookService.addBook(book);
            return "redirect:/controlPanel";
        } else {
            return "redirect:/authControlPanel";
        }
    }

    @PostMapping("/books/get")
    public String getBook(@ModelAttribute("hash") Long hash, HttpSession session, Model model) {
        if (isAuthorizedLibrarian(session)) {
            model.addAttribute("librarian", getLibrarian(session));
            model.addAttribute("books", bookService.findAll());
            model.addAttribute("rhash", librarianService.findBook(hash));
            return "controlPanel";
        } else {
            return "redirect:/authControlPanel";
        }
    }

    @PostMapping("/books/claim")
    public String claimBook(@ModelAttribute("id") Long bookId, HttpSession session) {
        if (isAutorizedReader(session)) {
            bookService.claimBook(bookId, getReader(session).getId());
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/books/return")
    public String returnBook(@ModelAttribute("id") Long bookId, HttpSession session) {
        if (isAutorizedReader(session)) {
            bookService.returnBook(bookId);
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }
}
