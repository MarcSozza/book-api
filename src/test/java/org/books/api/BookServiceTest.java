package org.books.api;

import org.books.api.models.Book;
import org.books.api.services.BookService;
import org.books.api.services.BookServiceUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @Autowired
    private BookService bookService;

    @Nested
    @Tag("verification_creation_livre")
    @DisplayName("Vérification de la création d'un nouveau livre")
    class VerificationCreationLivre {

        @Test
        @DisplayName("Renvoyer une erreur si le livre à créer est déjà présent")
        public void shouldReturnErrorIfLivreAlreadyExist() {
            Book book = new Book("Titre 1", "Stephen King", "2002", "peur", "résumé", 542);
            when(bookServiceUtils.isAlreadyKnown(any(Book.class), anyList())).thenReturn(true);

            assertThrows(RuntimeException.class, () -> {
                bookService.createLivre(book);
            });

            verify(bookServiceUtils, times(1)).isAlreadyKnown(any(Book.class), anyList());
        }

    }
}
