package ru.shashulovskiy.libraryorganizer.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Reader extends Person {
    @OneToMany(mappedBy = "holder", fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<>();

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(final Set<Book> books) {
        this.books = books;
    }
}
