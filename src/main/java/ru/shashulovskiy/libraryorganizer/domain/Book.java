package ru.shashulovskiy.libraryorganizer.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(
        indexes = {@Index(columnList = "title"), @Index(columnList = "author"), @Index(columnList = "reader_id")},
        uniqueConstraints = @UniqueConstraint(columnNames = "hash")
)
public class Book implements Comparable<Book> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reader_id")
    private Reader holder;

    private Boolean isClaimed;

    @Column(name = "hash")
    private Long hash;

    @Override
    public int compareTo(final Book o) {
        return id.compareTo(o.id);
    }
}
