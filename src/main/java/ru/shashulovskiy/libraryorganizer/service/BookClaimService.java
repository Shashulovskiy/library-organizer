package ru.shashulovskiy.libraryorganizer.service;

import org.springframework.stereotype.Service;
import ru.shashulovskiy.libraryorganizer.domain.BookClaim;
import ru.shashulovskiy.libraryorganizer.repository.BookClaimRepository;
import ru.shashulovskiy.libraryorganizer.repository.BookRepository;
import ru.shashulovskiy.libraryorganizer.repository.ReaderRepository;

@Service
public class BookClaimService {
    private final BookClaimRepository bookClaimRepository;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;

    public BookClaimService(final BookClaimRepository bookClaimRepository, final ReaderRepository readerRepository, final BookRepository bookRepository) {
        this.bookClaimRepository = bookClaimRepository;
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;
    }

    public BookClaim clamBook(Long readerId, Long bookId) {
        BookClaim bookClaim = new BookClaim();
        bookClaim.setBook(bookRepository.findById(bookId).orElse(null));
        bookClaim.setReader(readerRepository.findById(readerId).orElse(null));
        return bookClaimRepository.save(bookClaim);
    }

    public Iterable<BookClaim> findAll() {
        return bookClaimRepository.findAll();
    }

    public void acceptClaim(final Long claimId) {
        BookClaim bookClaim = bookClaimRepository.findById(claimId).orElse(null);
        bookRepository.assignBook(bookClaim.getBook().getId(), bookClaim.getReader());
        bookClaimRepository.delete(bookClaim);
    }

    public void declineClaim(final Long claimId) {
        BookClaim bookClaim = bookClaimRepository.findById(claimId).orElse(null);
        bookRepository.updateClaimedStatus(bookClaim.getBook().getId(), false);
        bookClaimRepository.delete(bookClaim);
    }
}
