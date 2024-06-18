package org.books.api.it;

import org.books.api.BookAlreadyExistException;
import org.books.api.models.Book;
import org.books.api.services.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
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

        @Test
        @DisplayName("Renvoyer une 201 si le livre à créer n'existe pas dans la base de données")
        void givenBookShouldReturn201() throws Exception {
            Book bookToAdd = new Book("Title 1", "Stephen King", "2000", "horreur", "resume", 520);

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
            Book bookToAdd = new Book("Title 1", "Stephen King", "2000", "horreur", "resume", 520);

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
    }
}
