package org.books.api;

import org.books.api.enums.Genre;
import org.books.api.errors.*;
import org.books.api.models.Book;
import org.books.api.repositories.BookRepository;
import org.books.api.services.BookService;
import org.books.api.services.BookServiceUtils;
import org.junit.jupiter.api.*;
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

    private Book bookToAdd;

    @Nested
    @Tag("verification_creation_livre")
    @DisplayName("Vérification de la création d'un nouveau livre")
    class VerificationCreationLivre {

        @BeforeEach
        void setup() {
            bookToAdd = new Book("Titre 1", "Stephen King", "2002", Genre.FANTASY, "résumé", 542);
        }

        @Nested
        @Tag("verification_already_known_book")
        @DisplayName("Vérification de l'existance du livre")
        class VerificationAlreadyKnownBook {
            @Test
            @DisplayName("Renvoyer le livre créé en DB si le livre à créer n'est pas présent")
            void shoulReturnNewLivreIfLivreNotExist() {
                when(bookServiceUtils.isAlreadyKnown(any(Book.class), anyList())).thenReturn(false);
                when(bookServiceUtils.isPublicationYearInPastOrPresent(any(Book.class))).thenReturn(true);
                when(bookServiceUtils.isNumberOfPagesBetweenRange(any(Book.class))).thenReturn(true);
                when(bookRepository.countBooksByAuthor(any(String.class))).thenReturn(10L);
                when(bookServiceUtils.isAuthorBookLimitReached(any(Long.class))).thenReturn(false);
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
                verify(bookServiceUtils, times(1)).isPublicationYearInPastOrPresent(any(Book.class));
                verify(bookRepository, times(1)).countBooksByAuthor(any(String.class));
                verify(bookServiceUtils, times(1)).isAuthorBookLimitReached(any(Long.class));
                verify(bookServiceUtils, times(1)).isNumberOfPagesBetweenRange(any(Book.class));
            }

            @Test
            @DisplayName("Renvoyer une erreur si le livre à créer est déjà présent")
            void shouldReturnErrorIfLivreAlreadyExist() {
                when(bookServiceUtils.isAlreadyKnown(any(Book.class), anyList())).thenReturn(true);
                when(bookServiceUtils.isPublicationYearInPastOrPresent(any(Book.class))).thenReturn(true);
                when(bookServiceUtils.isNumberOfPagesBetweenRange(any(Book.class))).thenReturn(true);

                CustomErrorException value = assertThrows(BookAlreadyExistException.class, () -> {
                    bookService.createBook(bookToAdd);
                });

                assertThat(value.getErrorCode()).isEqualTo(ErrorCode.BOOK_ALREADY_EXIST.getCode());

                verify(bookServiceUtils, times(1)).isAlreadyKnown(any(Book.class), anyList());
                verify(bookServiceUtils, times(1)).isPublicationYearInPastOrPresent(any(Book.class));
            }

        }

        @Nested
        @Tag("year_publication")
        @DisplayName("Vérification de la cohérence de l'année de publication")
        class VerificationYearPublication {

            @Test
            @DisplayName("Si l'année de publication est dans le futur, une erreur doit être remontée")
            void shouldReturnErrorIfYearPublicationInTheFuture() {
                when(bookServiceUtils.isPublicationYearInPastOrPresent(any(Book.class))).thenReturn(false);

                CustomErrorException result = assertThrows(YearInTheFuture.class, () -> {
                    bookService.createBook(bookToAdd);
                });

                assertThat(result.getErrorCode()).isEqualTo(ErrorCode.YEAR_IN_THE_FUTURE.getCode());

                verify(bookServiceUtils, times(1)).isPublicationYearInPastOrPresent(any(Book.class));
            }

            @Test
            @DisplayName("Si l'année de publication n'est pas valide, une erreur doit être remontée")
            void shouldReturnErrorIfYearPublicationNotEhaustive() {
                String errorYear = "Je suis une fausse année";
                bookToAdd = new Book("Titre 1", "Stephen King", errorYear, Genre.ROMANCE, "résumé", 542);
                when(bookServiceUtils.isPublicationYearInPastOrPresent(any(Book.class))).thenThrow(
                        new NotExhaustiveNumber(errorYear));
                CustomErrorException result = assertThrows(NotExhaustiveNumber.class, () -> {
                    bookService.createBook(bookToAdd);
                });

                assertThat(result.getErrorCode()).isEqualTo(ErrorCode.NOT_EXHAUSTIVE_NUMBER.getCode());

                verify(bookServiceUtils, times(1)).isPublicationYearInPastOrPresent(any(Book.class));
            }
        }

        @Nested
        @Tag("verification_page_size")
        @DisplayName("Vérification du nombre de page du livre")
        class VerificationPageSize {
            @Test
            @DisplayName("Si le livre à ajouter à un nombre de page inférieur au minimum, renvoie une erreur")
            void shouldReturnErrorIfPageSizeInferiorAtMinimum() {
                bookToAdd.setPageCount(9);
                when(bookServiceUtils.isPublicationYearInPastOrPresent(bookToAdd)).thenReturn(true);
                when(bookServiceUtils.isNumberOfPagesBetweenRange(bookToAdd)).thenReturn(false);
                CustomErrorException exception = assertThrows(NotInRangeNumberOfPages.class,
                                                              () -> bookService.createBook(bookToAdd));
                assertThat(exception.getErrorCode()).isEqualTo(
                        ErrorCode.NOT_IN_THE_RANGE_NUMBER_OF_PAGE.getCode());
                verify(bookServiceUtils, times(1)).isPublicationYearInPastOrPresent(any(Book.class));
                verify(bookServiceUtils, times(1)).isNumberOfPagesBetweenRange(bookToAdd);
            }

            @Test
            @DisplayName("Si le livre à ajouter à un nombre de page supérieur à la limite, renvoie une erreur")
            void shouldReturnErrorIfPageSizeSuperiorAtMaximum() {
                bookToAdd.setPageCount(999999);
                when(bookServiceUtils.isNumberOfPagesBetweenRange(bookToAdd)).thenReturn(false);
                when(bookServiceUtils.isPublicationYearInPastOrPresent(bookToAdd)).thenReturn(true);
                CustomErrorException exception = assertThrows(NotInRangeNumberOfPages.class,
                                                              () -> bookService.createBook(bookToAdd));
                assertThat(exception.getErrorCode()).isEqualTo(
                        ErrorCode.NOT_IN_THE_RANGE_NUMBER_OF_PAGE.getCode());
                verify(bookServiceUtils, times(1)).isNumberOfPagesBetweenRange(bookToAdd);
                verify(bookServiceUtils, times(1)).isPublicationYearInPastOrPresent(bookToAdd);
            }
        }

        @Nested
        @Tag("verification_nombre_livre_auteur")
        @DisplayName("Vérification du nombre de livre de l'auteur")
        class VerificationNombreLivreAuteur {

            @Test
            @DisplayName("Si le livre à ajouter dépasse la limite autorisée pour un auteur, renvoie une erreur")
            void shouldReturnErrorIfNumberOfBookReachMaxAutorized() {
                Long numberOfPage = 95L;
                when(bookServiceUtils.isPublicationYearInPastOrPresent(any(Book.class))).thenReturn(true);
                when(bookServiceUtils.isNumberOfPagesBetweenRange(any(Book.class))).thenReturn(true);
                when(bookRepository.countBooksByAuthor(any(String.class))).thenReturn(numberOfPage);
                when(bookServiceUtils.isAuthorBookLimitReached(numberOfPage)).thenReturn(true);

                CustomErrorException exception = assertThrows(NotInRangeMaxBook.class,
                                                              () -> bookService.createBook(bookToAdd));

                assertThat(exception.getErrorCode()).isEqualTo(
                        ErrorCode.NOT_IN_THE_RANGE_MAX_BOOK.getCode());

                verify(bookServiceUtils, times(1)).isPublicationYearInPastOrPresent(any(Book.class));
                verify(bookServiceUtils, times(1)).isNumberOfPagesBetweenRange(any(Book.class));
                verify(bookRepository, times(1)).countBooksByAuthor(any(String.class));
                verify(bookServiceUtils, times(1)).isAuthorBookLimitReached(numberOfPage);
            }
        }

    }


}
