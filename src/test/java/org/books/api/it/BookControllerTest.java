package org.books.api.it;

import org.books.api.errors.BookAlreadyExistException;
import org.books.api.errors.NotExhaustiveYear;
import org.books.api.errors.UnexpectedNumberOfPage;
import org.books.api.errors.YearInTheFuture;
import org.books.api.models.Book;
import org.books.api.services.BookService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Nested
    @Tag("create_book")
    @DisplayName("Test de la route POST /book")
    class create_book {

        private Book bookToAdd;

        @BeforeEach
        void setup() {
            bookToAdd = new Book("Title 1", "Stephen King", "2000", "horreur", "resume", 520);
        }

        @Test
        @DisplayName("Renvoyer une 201 si le livre à créer n'existe pas dans la base de données")
        void givenBookShouldReturn201() throws Exception {
            Mockito.when(bookService.createBook(bookToAdd))
                   .thenReturn(bookToAdd);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/books/book")
                                                  .content(
                                                          "{\"title\":\"Title 1\",\"author\":\"Stephen King\",\"yearOfPublication\":\"2000\",\"genre\":\"horreur\",\"summary\":\"resume\",\"pageCount\":520}")
                                                  .contentType(
                                                          MediaType.APPLICATION_JSON))
                   .andExpect(status().isCreated());

            Mockito.verify(bookService, Mockito.times(1))
                   .createBook(bookToAdd);
        }

        @Test
        @DisplayName("Renvoyer une 409 si le livre à créer existe dans la base de données")
        void givenBookShouldReturn409() throws Exception {
            Mockito.when(bookService.createBook(any(Book.class)))
                   .thenThrow(BookAlreadyExistException.class);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/books/book")
                                                  .content(
                                                          "{\"title\":\"Title 1\",\"author\":\"Stephen King\",\"yearOfPublication\":\"2000\",\"genre\":\"horreur\",\"summary\":\"resume\",\"pageCount\":520}")
                                                  .contentType(
                                                          MediaType.APPLICATION_JSON))
                   .andExpect(status().isConflict());

            Mockito.verify(bookService, Mockito.times(1))
                   .createBook(bookToAdd);

        }

        @Test
        @DisplayName("Renvoyer une 400 si l'année du livre à créer est supérieur à la date d'aujourd'hui")
        void givenBookWithFutureDateShouldReturn400() throws Exception {
            bookToAdd = new Book("Title 1", "Stephen King", "2040", "horreur", "resume", 520);

            Mockito.when(bookService.createBook(any(Book.class)))
                   .thenThrow(YearInTheFuture.class);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/books/book")
                                                  .content(
                                                          "{\"title\":\"Title 1\",\"author\":\"Stephen King\",\"yearOfPublication\":\"2040\",\"genre\":\"horreur\",\"summary\":\"resume\",\"pageCount\":520}")
                                                  .contentType(
                                                          MediaType.APPLICATION_JSON))
                   .andExpect(status().isBadRequest());

            Mockito.verify(bookService, Mockito.times(1))
                   .createBook(bookToAdd);
        }

        @Test
        @DisplayName("Renvoyer une 400 si l'année du livre à créer n'est pas une date valide")
        void givenBookWithInvalidDateShouldReturn400() throws Exception {
            String invalidYear = "Je ne suis pas une année";
            Book invalidBook = new Book("Title 1", "Stephen King", invalidYear, "horreur", "resume", 520);

            Mockito.when(bookService.createBook(invalidBook))
                   .thenThrow(NotExhaustiveYear.class);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/books/book")
                                                  .content(
                                                          "{\"title\":\"Title 1\",\"author\":\"Stephen King\",\"yearOfPublication\":\"Je ne suis pas une année\",\"genre\":\"horreur\",\"summary\":\"resume\",\"pageCount\":520}")
                                                  .contentType(MediaType.APPLICATION_JSON))
                   .andExpect(status().isBadRequest());

            Mockito.verify(bookService, Mockito.times(1))
                   .createBook(invalidBook);
        }

        @Test
        @DisplayName("Renvoyer une 400 si le nombre de page est inférieur à la limite")
        void givenBookWithInvalidPageCountShouldReturn400() throws Exception {
            bookToAdd.setPageCount(6);

            Mockito.when(bookService.createBook(bookToAdd))
                   .thenThrow(UnexpectedNumberOfPage.class);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/books/book")
                                                  .content(
                                                          "{\"title\":\"Title 1\",\"author\":\"Stephen King\",\"yearOfPublication\":\"2000\",\"genre\":\"horreur\",\"summary\":\"resume\",\"pageCount\":6}")
                                                  .contentType(MediaType.APPLICATION_JSON))
                   .andExpect(status().isBadRequest());
            Mockito.verify(bookService, Mockito.times(1))
                   .createBook(bookToAdd);
        }

    }
}
