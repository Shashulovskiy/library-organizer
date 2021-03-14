package ru.shashulovskiy.libraryorganizer.service;

import org.springframework.stereotype.Service;
import ru.shashulovskiy.libraryorganizer.domain.Book;
import ru.shashulovskiy.libraryorganizer.domain.Librarian;
import ru.shashulovskiy.libraryorganizer.domain.Reader;
import ru.shashulovskiy.libraryorganizer.repository.BookRepository;
import ru.shashulovskiy.libraryorganizer.repository.LibrarianRepository;
import ru.shashulovskiy.libraryorganizer.repository.ReaderRepository;

@Service
public class LibrarianService {
    private final LibrarianRepository librarianRepository;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;

    public LibrarianService(final LibrarianRepository librarianRepository, final ReaderRepository readerRepository, final BookRepository bookRepository) {
        this.librarianRepository = librarianRepository;
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;
    }

    public Librarian employLibrarian(Librarian librarian) {
        return librarianRepository.save(librarian);
    }

    public Reader registerReader(Reader reader) {
        return readerRepository.save(reader);
    }

    public Reader addReader(Reader reader) {
        return readerRepository.save(reader);
    }

    public void assignBook(Long bookId, Reader reader) {
        bookRepository.assignBook(bookId, reader);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book findBookByHash(Long hash) {
        return bookRepository.findByHash(hash);
    }

    public void removeBook(Book book) {
        if (book.getHolder() == null) {
            bookRepository.delete(book);
        }
    }

    public void updateBook(Long id, Long newHash) {
        bookRepository.updateHash(id, newHash);
    }

    public String findBook(Long hash) {
        Book book = findBookByHash(hash);
        if (book == null) {
            return "Book not found";
        } else {
            return String.format("Hash %d belongs to %s by %s", hash, book.getTitle(), book.getAuthor());
        }
    }
}
