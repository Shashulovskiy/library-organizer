package ru.shashulovskiy.libraryorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shashulovskiy.libraryorganizer.domain.Librarian;

import java.util.Optional;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
    Optional<Librarian> findByLogin(String login);
}
