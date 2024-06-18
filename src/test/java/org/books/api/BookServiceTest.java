package org.books.api;

import org.books.api.models.Book;
import org.books.api.repositories.BookRepository;
import org.books.api.services.BookService;
import org.books.api.services.BookServiceUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
@Tag("BookService_Test")
@DisplayName("Test des méthodes de la classe BookService")
public class BookServiceTest {

    @MockBean
    private BookServiceUtils bookServiceUtils;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Nested
    @Tag("verification_creation_livre")
    @DisplayName("Vérification de la création d'un nouveau livre")
    class VerificationCreationLivre {

        @Test
        @DisplayName("Renvoyer une erreur si le livre à créer est déjà présent")
        void shouldReturnErrorIfLivreAlreadyExist() {
            Book book = new Book("Titre 1", "Stephen King", "2002", "peur", "résumé", 542);
            when(bookServiceUtils.isAlreadyKnown(any(Book.class), anyList())).thenReturn(true);

            RuntimeException value = assertThrows(RuntimeException.class, () -> {
                bookService.createBook(book);
            });

            assertThat(value.getMessage()).contains("Book already exists");

            verify(bookServiceUtils, times(1)).isAlreadyKnown(any(Book.class), anyList());
        }

        @Test
        @DisplayName("Renvoyer le livre créé en DB si le livre à créer n'est pas présent")
        void shoulReturnNewLivreIfLivreNotExist() {
            Book bookToAdd = new Book("Titre 1", "Stephen King", "2002", "peur", "résumé", 542);
            when(bookServiceUtils.isAlreadyKnown(any(Book.class), anyList())).thenReturn(false);
            when(bookRepository.save(any(Book.class))).thenReturn(bookToAdd);

            Book bookAdded = bookService.createBook(bookToAdd);

            assertThat(bookAdded.getAuthor()).isEqualTo(bookToAdd.getAuthor());
            assertThat(bookAdded.getGenre()).isEqualTo(bookToAdd.getGenre());
            assertThat(bookAdded.getSummary()).isEqualTo(bookToAdd.getSummary());
            assertThat(bookAdded.getTitle()).isEqualTo(bookToAdd.getTitle());
            assertThat(bookAdded.getYearOfPublication()).isEqualTo(bookToAdd.getYearOfPublication());
            assertThat(bookAdded.getPageCount()).isEqualTo(bookToAdd.getPageCount());

            verify(bookRepository, times(1)).save(any(Book.class));
            verify(bookServiceUtils, times(1)).isAlreadyKnown(any(Book.class), anyList());
        }

    }
}
