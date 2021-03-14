package ru.shashulovskiy.libraryorganizer.domain;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
@Inheritance
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    private String login;
}
