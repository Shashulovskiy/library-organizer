package ru.shashulovskiy.libraryorganizer.service;

import org.springframework.stereotype.Service;
import ru.shashulovskiy.libraryorganizer.domain.Book;
import ru.shashulovskiy.libraryorganizer.domain.BookClaim;
import ru.shashulovskiy.libraryorganizer.repository.BookClaimRepository;
import ru.shashulovskiy.libraryorganizer.repository.BookRepository;
import ru.shashulovskiy.libraryorganizer.repository.ReaderRepository;

import java.util.Objects;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final BookClaimRepository bookClaimRepository;

    public BookService(final BookRepository bookRepository, final ReaderRepository readerRepository, final BookClaimRepository bookClaimRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.bookClaimRepository = bookClaimRepository;
    }

    public void removeBook(Long id) {
        bookRepository.delete(Objects.requireNonNull(bookRepository.findById(id).orElse(null)));
    }

    public void updateBookHash(final Long id, final Long newHash) {
        bookRepository.updateHash(id, newHash);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    public Iterable<Book> finaAllAvailable() {
        return bookRepository.findByHolderIsNull();
    }

    public BookClaim claimBook(final Long bookId, final Long readerId) {
        BookClaim bookClaim = new BookClaim();
        bookClaim.setBook(bookRepository.findById(bookId).orElse(null));
        bookClaim.setReader(readerRepository.findById(readerId).orElse(null));
        bookRepository.updateClaimedStatus(bookId, true);
        return bookClaimRepository.save(bookClaim);
    }

    public void returnBook(final Long bookId) {
        bookRepository.updateClaimedStatus(bookId, false);
        bookRepository.assignBook(bookId, null);
    }
}
