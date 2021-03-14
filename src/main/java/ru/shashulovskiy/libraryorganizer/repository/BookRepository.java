package ru.shashulovskiy.libraryorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.shashulovskiy.libraryorganizer.domain.Book;
import ru.shashulovskiy.libraryorganizer.domain.Reader;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Modifying
    @Transactional
    @Query("update Book b set b.hash = :hash where b.id = :id")
    void updateHash(@Param(value = "id") Long id, @Param(value = "hash") Long hash);

    @Modifying
    @Transactional
    @Query("update Book b set b.isClaimed = :isClaimed where b.id = :id")
    void updateClaimedStatus(@Param(value = "id") Long id, @Param(value = "isClaimed") Boolean isClaimed);

    @Modifying
    @Transactional
    @Query("update Book b set b.holder = :holder where b.id = :id")
    void assignBook(@Param(value = "id") Long id, @Param(value = "holder") Reader holder);

    Book findByHash(Long hash);

    Iterable<Book> findByHolderIsNull();
}
