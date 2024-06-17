package org.books.api.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Book_loans")
public class BookLoans {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Column(name = "user_id")
    public String userId;

    @Column(name = "book_id")
    public String bookId;


}
