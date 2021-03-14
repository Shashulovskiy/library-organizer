package ru.shashulovskiy.libraryorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shashulovskiy.libraryorganizer.domain.BookClaim;

public interface BookClaimRepository extends JpaRepository<BookClaim, Long> {
}
