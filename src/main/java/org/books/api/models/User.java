package org.books.api.models;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "Utilisateur")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String password;
}
