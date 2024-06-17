package org.books.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    public String email;

    public String password;
}
