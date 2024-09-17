package org.books.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Genre")
public class GenreEntity {
    @Id
    private int id;

    private String name;
}
