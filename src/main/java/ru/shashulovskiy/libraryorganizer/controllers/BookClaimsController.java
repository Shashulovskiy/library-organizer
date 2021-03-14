package ru.shashulovskiy.libraryorganizer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.shashulovskiy.libraryorganizer.service.BookClaimService;

import javax.servlet.http.HttpSession;

@Controller
public class BookClaimsController extends AbstractPageController {
    private final BookClaimService bookClaimService;

    public BookClaimsController(final BookClaimService bookClaimService) {
        this.bookClaimService = bookClaimService;
    }

    @PostMapping("/claims/accept")
    public String acceptClaim(@ModelAttribute("id") Long claimId, HttpSession session) {
        if (isAuthorizedLibrarian(session)) {
            bookClaimService.acceptClaim(claimId);
            return "redirect:/controlPanel";
        } else {
            return "redirect:/authControlPanel";
        }
    }

    @PostMapping("/claims/decline")
    public String declineClaim(@ModelAttribute("id") Long claimId, HttpSession session) {
        if (isAuthorizedLibrarian(session)) {
            bookClaimService.declineClaim(claimId);
            return "redirect:/controlPanel";
        } else {
            return "redirect:/authControlPanel";
        }
    }


}
