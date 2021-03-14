package ru.shashulovskiy.libraryorganizer.service;

import org.springframework.stereotype.Service;
import ru.shashulovskiy.libraryorganizer.domain.Librarian;
import ru.shashulovskiy.libraryorganizer.domain.Reader;
import ru.shashulovskiy.libraryorganizer.repository.LibrarianRepository;
import ru.shashulovskiy.libraryorganizer.repository.ReaderRepository;

@Service
public class LoginService {
    private final ReaderRepository readerRepository;
    private final LibrarianRepository librarianRepository;

    public LoginService(final ReaderRepository readerRepository, final LibrarianRepository librarianRepository) {
        this.readerRepository = readerRepository;
        this.librarianRepository = librarianRepository;
    }

    public Reader findReader(String login) {
        return readerRepository.findByLogin(login).orElse(null);
    }

    public Librarian findLibrarian(String login) {
        return librarianRepository.findByLogin(login).orElse(null);
    }

}
