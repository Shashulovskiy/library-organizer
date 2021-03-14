package ru.shashulovskiy.libraryorganizer.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.shashulovskiy.libraryorganizer.domain.Book;
import ru.shashulovskiy.libraryorganizer.domain.Librarian;
import ru.shashulovskiy.libraryorganizer.domain.Reader;
import ru.shashulovskiy.libraryorganizer.repository.BookRepository;
import ru.shashulovskiy.libraryorganizer.repository.LibrarianRepository;
import ru.shashulovskiy.libraryorganizer.repository.ReaderRepository;
import ru.shashulovskiy.libraryorganizer.service.LibrarianService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RepositoryBootstap implements CommandLineRunner {

    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final LibrarianRepository librarianRepository;
    private final LibrarianService librarianService;

    public RepositoryBootstap(final ReaderRepository readerRepository, final BookRepository bookRepository, final LibrarianRepository librarianRepository, final LibrarianService librarianService) {
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;
        this.librarianRepository = librarianRepository;
        this.librarianService = librarianService;
    }

    @Override
    public void run(final String... args) throws Exception {
        List<Reader> readers = getReaders();
        readerRepository.saveAll(readers);

        // TODO: Refactor
        Book book = bookRepository.save(buildBook("Harry Potter", "J. K. Rowling", 12345L));
        librarianService.assignBook(book.getId(), readers.get(0));

        book = bookRepository.save(buildBook("The Hobbit", "J. R. R. Tolkien", 98765L));
        librarianService.assignBook(book.getId(), readers.get(0));

        book = bookRepository.save(buildBook("The Cherry Orchard", "Anton Chekhov", 13579L));
        librarianService.assignBook(book.getId(), readers.get(0));

        // Unclaimed Books
        bookRepository.save(buildBook("Dandelion Wine", "Ray Bradbury", 36364L));
        bookRepository.save(buildBook("The Long Goodbye", "Raymond Chandler", 872572L));
        bookRepository.save(buildBook("The Silent Companions", "Laura Purcell", 14637L));

        librarianRepository.save(buildLibrarian("Zaal", "Judie", "testLibrarian"));
    }

    private List<Reader> getReaders() {
        List<Reader> readers = new ArrayList<>();

        readers.add(buildReader("Molly", "Guerrero", "testUser",
                List.of(buildBook("Harry Potter", "J. K. Rowling", 12345L))));

        readers.add(buildReader("Bobby", "Jenning", "abacaba",
                Collections.emptyList()));
        readers.add(buildReader("Kendall", "Tillery", "coolLogin",
                Collections.emptyList()));

        return readers;
    }

    private Librarian buildLibrarian(String firstName, String lastName, String login) {
        Librarian librarian = new Librarian();
        librarian.setFirstName(firstName);
        librarian.setLastName(lastName);
        librarian.setLogin(login);
        return librarian;
    }

    private Book buildBook(String title, String author, Long hash) {
        Book book = new Book();
        book.setAuthor(author);
        book.setHash(hash);
        book.setTitle(title);
        book.setIsClaimed(false);
        return book;
    }

    Reader buildReader(String firstName, String lastName, String login, List<Book> books) {
        Reader reader = new Reader();
        reader.setFirstName(firstName);
        reader.setLastName(lastName);
        reader.setLogin(login);
        return reader;
    }
}
