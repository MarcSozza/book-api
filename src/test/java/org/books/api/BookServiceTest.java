package org.books.api;

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
            bookToAdd = new Book("Titre 1", "Stephen King", "2002", "peur", "résumé", 542);
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
            }

            @Test
            @DisplayName("Renvoyer une erreur si le livre à créer est déjà présent")
            void shouldReturnErrorIfLivreAlreadyExist() {
                when(bookServiceUtils.isAlreadyKnown(any(Book.class), anyList())).thenReturn(true);
                when(bookServiceUtils.isPublicationYearInPastOrPresent(any(Book.class))).thenReturn(true);

                CustomErrorException value = assertThrows(BookAlreadyExistException.class, () -> {
                    bookService.createBook(bookToAdd);
                });

                assertThat(value.getErrorCode()).isEqualTo(ErrorCode.ERROR_CODE_BOOK_ALREADY_EXIST.getCode());

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

                assertThat(result.getErrorCode()).isEqualTo(ErrorCode.ERROR_CODE_YEAR_IN_THE_FUTURE.getCode());

                verify(bookServiceUtils, times(1)).isPublicationYearInPastOrPresent(any(Book.class));
            }

            @Test
            @DisplayName("Si l'année de publication n'est pas valide, une erreur doit être remontée")
            void shouldReturnErrorIfYearPublicationNotEhaustive() {
                String errorYear = "Je suis une fausse année";
                bookToAdd = new Book("Titre 1", "Stephen King", errorYear, "peur", "résumé", 542);
                when(bookServiceUtils.isPublicationYearInPastOrPresent(any(Book.class))).thenThrow(
                        new NotExhaustiveYear(errorYear));
                CustomErrorException result = assertThrows(NotExhaustiveYear.class, () -> {
                    bookService.createBook(bookToAdd);
                });

                assertThat(result.getErrorCode()).isEqualTo(ErrorCode.ERROR_CODE_NOT_EXHAUSTIVE_YEAR.getCode());

                verify(bookServiceUtils, times(1)).isPublicationYearInPastOrPresent(any(Book.class));
            }
        }

    }


}
