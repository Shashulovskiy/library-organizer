package ru.shashulovskiy.libraryorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shashulovskiy.libraryorganizer.domain.Reader;

import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Optional<Reader> findByLogin(String login);
}
