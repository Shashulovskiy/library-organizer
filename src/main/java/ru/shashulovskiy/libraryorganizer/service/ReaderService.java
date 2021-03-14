package ru.shashulovskiy.libraryorganizer.service;

import org.springframework.stereotype.Service;
import ru.shashulovskiy.libraryorganizer.domain.Book;
import ru.shashulovskiy.libraryorganizer.domain.Reader;
import ru.shashulovskiy.libraryorganizer.repository.ReaderRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;

    public ReaderService(final ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public Reader findById(final Long id) {
        return readerRepository.findById(id).orElse(null);
    }

    public Reader addReader(final Reader reader) {
        return readerRepository.save(reader);
    }

    public Set<Book> getBooks(final Long id) {
        return findById(id).getBooks();
    }

    public String getReadersBooks(final Long readerId) {
        Set<Book> readersBooks = getBooks(readerId);
        if (readersBooks.isEmpty()) {
            return "Reader has no books";
        } else {
            return readersBooks.stream().map(Book::getTitle).collect(Collectors.joining(", "));
        }
    }
}
