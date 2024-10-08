package org.books.api;

import org.books.api.enums.Genre;
import org.books.api.errors.CustomErrorException;
import org.books.api.errors.ErrorCode;
import org.books.api.errors.NotExhaustiveNumber;
import org.books.api.models.Book;
import org.books.api.services.BookServiceUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("BookServiceUtils_Test")
@DisplayName("Tests des utilitaires de la classe Book Service Utils")
public class BookServiceUtilsTest {
    @Autowired
    private BookServiceUtils bookServiceUtils;

    private ArrayList<Book> books;

    /*
     * Règle métier : Un livre avec le même titre
     *  et le même auteur ne peut pas être ajouté à nouveau.
     *
     * */
    @Nested
    @Tag("verification_livre_à_ajouter")
    @DisplayName("Vérification de l'existance du livre à ajouter dans une liste de livres")
    class CheckExistanceDuLivreDansLaBase {

        @BeforeEach
        public void setUp() {
            books = new ArrayList<>();
            books.add(new Book("Titre 1", "John Doe", "2000", Genre.BIOGRAPHY, "résumé", 500));
            books.add(new Book("Titre 2", "Jane Doe", "2002", Genre.FANTASY, "résumé", 268));
            books.add(new Book("Titre 3", "Stephen King", "1986", Genre.NON_FICTION, "description", 356));
            books.add(new Book("Titre 4", "Marc Lévy", "2003", Genre.ROMANCE, "résumé", 245));
        }

        @Test
        @DisplayName("Si aucun livre n'est trouvé alors renvoie faux")
        @Timeout(1)
        void givenUnknownBookShouldReturlFalseWhenNotInTheList() {
            Book bookToAdd = new Book("loul", "unknown author", "2006", Genre.BIOGRAPHY, "description", 586);
            boolean result = bookServiceUtils.isAlreadyKnown(bookToAdd, books);
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("S'il y a uniquement un livre qui à le même auteur que le livre à ajouter, alors renvoie faux")
        @Timeout(1)
        void givenUnknownBookShoulReturnFalseWhenThereIsOnlyABookWithSameAuteur() {
            Book bookToAdd = new Book("Test", "Stephen King", "1986", Genre.HISTORICAL, "description", 456);
            boolean result = bookServiceUtils.isAlreadyKnown(bookToAdd, books);
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("S'il y a uniqument un livre qui à le même titre que le livre à ajouter, alors renvoie faux")
        @Timeout(1)
        void givenUnknownBookShouldReturnFalseWhenThereIsOnlyABookWithSameTitle() {
            Book bookToAdd = new Book("Titre 1", "test test", "2006", Genre.NON_FICTION, "description", 586);
            boolean result = bookServiceUtils.isAlreadyKnown(bookToAdd, books);
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("S'il y a uniquement un livre qui à le même auteur et le même titre que le livre à ajouter alors renvoie vrai")
        @Timeout(1)
        void givenKnownBookShouldReturnTrueWhenNotInTheList() {
            Book bookToAdd = new Book("Titre 1", "John Doe", "2002", Genre.SCIENCE_FICTION, "description", 452);
            boolean result = bookServiceUtils.isAlreadyKnown(bookToAdd, books);
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Si la liste est vide, alors renvoie faux")
        void givenBookShouldReturnTrueWhenEmptyList() {
            ArrayList<Book> emptyList = new ArrayList<>();
            Book bookToAdd = new Book("Titre 1", "John Doe", "2002", Genre.FANTASY, "description", 452);
            boolean result = bookServiceUtils.isAlreadyKnown(bookToAdd, emptyList);
            assertThat(result).isFalse();
        }

    }

    @Nested
    @Tag("verification_annee_publication")
    @DisplayName("Vérification de l'année de publication du livre à ajouter")
    class CheckAnneePublicationDuLivre {

        @Test
        @DisplayName("Si l'année de publication est inférieur à aujourd'hui, alors renvoie vrai")
        void shouldReturnTrueWhenYearInferiorAtToday() {
            Book bookToAdd = new Book("Titre 1", "John Doe", "2002", Genre.FANTASY, "description", 452);
            boolean result = bookServiceUtils.isPublicationYearInPastOrPresent(bookToAdd);
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Si l'année de publication correspond à celle d'aujourd'hui, alors renvoie vrai")
        void shouldReturnTrueWhenYearEqualsAtToday() {
            Book bookToAdd = new Book("Titre 1", "John Doe", String.valueOf(Year.now()
                                                                                .getValue()), Genre.HORROR, "description",
                                      452);
            boolean result = bookServiceUtils.isPublicationYearInPastOrPresent(bookToAdd);
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Si l'année de publication est supérieur à celle d'aujourd'hui, alors renvoie faux")
        void shouldReturnFalseWhenYearSuperiorAtToday() {
            Book bookToAdd = new Book("Titre 1", "John Doe", "3000", Genre.FANTASY, "description", 452);
            boolean result = bookServiceUtils.isPublicationYearInPastOrPresent(bookToAdd);
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("Si l'année de publication n'est pas cohérente, renvoie une erreur")
        void shouldThrowErrorWhenYearIsNotExhaustive() {
            Book bookToAdd = new Book("Titre 1", "John Doe", "Je ne suis pas une date", Genre.BIOGRAPHY, "description", 452);
            CustomErrorException exception = assertThrows(NotExhaustiveNumber.class, () -> {
                bookServiceUtils.isPublicationYearInPastOrPresent(bookToAdd);
            });
            assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_EXHAUSTIVE_NUMBER.getCode());
        }
    }

    @Nested
    @Tag("verification_nombre_de_page")
    @DisplayName("Vérification du nombre de page du livre à ajouter")
    class CheckNombreDePageDuLivre {

        @Test
        @DisplayName("Si le nombre de page est supérieur ou égale à 10 et inférieur à la limite alors renvoie vrai")
        void shouldReturnTrueWhenNumberOfPageBetween10AndLimit() {
            int number = 10;
            Book bookToAdd = new Book("Titre 1", "John Doe", "2002", Genre.FICTION, "description", number);
            boolean result = bookServiceUtils.isNumberOfPagesBetweenRange(bookToAdd);
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Si le nombre est inférieur à 10, renvoie faux")
        void shouldReturnFalseWhenNumberOfPageInferiorAt10() {
            int number = 2;
            Book bookToAdd = new Book("Titre 1", "John Doe", "2002", Genre.ROMANCE, "description", number);
            boolean result = bookServiceUtils.isNumberOfPagesBetweenRange(bookToAdd);
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("Si le nombre est supérieur à la limite, renvoie faux")
        void shouldReturnFalseWhenNumberOfPageSuperiorAtLimit() {
            int number = 10000;
            Book bookToAdd = new Book("Titre 1", "John Doe", "2002", Genre.FANTASY, "description", number);
            boolean result = bookServiceUtils.isNumberOfPagesBetweenRange(bookToAdd);
            assertThat(result).isFalse();
        }
    }

    @Nested
    @Tag("Vérification_nombre_de_livre_auteur")
    @DisplayName("Vérification du nombre de livre de l'auteur en DB")
    class CheckNombreDeLivreAuteur {

        @Test
        @DisplayName("Si le livre à ajouter est dans la limite autorisée, alors renvoie faux")
        void shouldReturnFalseWhenNumberOfBookBetween0AndLimit() {
            Long numberOfBooks = 10L;
            boolean result = bookServiceUtils.isAuthorBookLimitReached(numberOfBooks);
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("Si le livre à ajouter dépasse la limite autorisée, alors renvoie vrai")
        void shouldReturnTrueWhenNumberOfBooksInTheLimit() {
            Long numberOfBooks = 200L;
            boolean result = bookServiceUtils.isAuthorBookLimitReached(numberOfBooks);
            assertThat(result).isTrue();
        }
    }
}
