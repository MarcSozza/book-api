package org.books.api;

import org.books.api.models.Book;
import org.books.api.services.BookServiceUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Tag("BookServiceUtils_Test")
@DisplayName("Tests des utilitaires de la classe Book Service Utils")
public class BookServiceUtilsTest {
    @Autowired
    private BookServiceUtils bookServiceUtils;

    private ArrayList<Book> books;

    @BeforeEach
    public void setUp() {
        books = new ArrayList<>();
        books.add(new Book("Titre 1", "John Doe", "2000", "peur", "résumé", 500));
        books.add(new Book("Titre 2", "Jane Doe", "2002", "Aventure", "résumé", 268));
        books.add(new Book("Titre 3", "Stephen King", "1986", "Peur", "description", 356));
        books.add(new Book("Titre 4", "Marc Lévy", "2003", "Romance", "résumé", 245));
    }

    /*
     * Règle métier : Un livre avec le même titre
     *  et le même auteur ne peut pas être ajouté à nouveau.
     *
     * */
    @Nested
    @Tag("verification_livre_à_ajouter")
    @DisplayName("Vérification de l'existance du livre à ajouter dans une liste de livres")
    class CheckExistanceDuLivreDansLaBase {

        @Test
        @DisplayName("Si aucun livre n'est trouvé alors renvoie faux")
        @Timeout(1)
        void givenUnknownBookShouldReturlFalseWhenNotInTheList() {
            Book bookToAdd = new Book("loul", "unknown author", "2006", "peur", "description", 586);
            boolean result = bookServiceUtils.isAlreadyKnown(bookToAdd, books);
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("S'il y a uniquement un livre qui à le même auteur que le livre à ajouter, alors renvoie faux")
        @Timeout(1)
        void givenUnknownBookShoulReturnFalseWhenThereIsOnlyABookWithSameAuteur() {
            Book bookToAdd = new Book("Test", "Stephen King", "1986", "thriller", "description", 456);
            boolean result = bookServiceUtils.isAlreadyKnown(bookToAdd, books);
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("S'il y a uniqument un livre qui à le même titre que le livre à ajouter, alors renvoie faux")
        @Timeout(1)
        void givenUnknownBookShouldReturnFalseWhenThereIsOnlyABookWithSameTitle() {
            Book bookToAdd = new Book("Titre 1", "test test", "2006", "peur", "description", 586);
            boolean result = bookServiceUtils.isAlreadyKnown(bookToAdd, books);
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("S'il y a uniquement un livre qui à le même auteur et le même titre que le livre à ajouter alors renvoie vrai")
        @Timeout(1)
        void givenKnownBookShouldReturnTrueWhenNotInTheList() {
            Book bookToAdd = new Book("Titre 1", "John Doe", "2002", "peur", "description", 452);
            boolean result = bookServiceUtils.isAlreadyKnown(bookToAdd, books);
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Si la liste est vide, alors renvoie faux")
        void givenBookShouldReturnTrueWhenEmptyList() {
            ArrayList<Book> emptyList = new ArrayList<>();
            Book bookToAdd = new Book("Titre 1", "John Doe", "2002", "peur", "description", 452);
            boolean result = bookServiceUtils.isAlreadyKnown(bookToAdd, emptyList);
            assertThat(result).isFalse();
        }
    }
}
