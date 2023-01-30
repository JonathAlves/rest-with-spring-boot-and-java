package br.com.jonathanestudo.apispring.unittests.mockito.services;

import br.com.jonathanestudo.apispring.data.dto.v1.BookDTO;
import br.com.jonathanestudo.apispring.data.dto.v1.PersonDTO;
import br.com.jonathanestudo.apispring.exceptions.RequiredObjectIsNullException;
import br.com.jonathanestudo.apispring.model.Book;
import br.com.jonathanestudo.apispring.model.Person;
import br.com.jonathanestudo.apispring.repositories.BookRepository;
import br.com.jonathanestudo.apispring.repositories.PersonRepository;
import br.com.jonathanestudo.apispring.services.BookService;
import br.com.jonathanestudo.apispring.services.PersonService;
import br.com.jonathanestudo.apispring.unittests.mapper.mocks.MockBook;
import br.com.jonathanestudo.apispring.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook input;
    LocalDateTime localDateTime = LocalDateTime.parse("2019-11-15T13:15:30");
    Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

    @InjectMocks
    private BookService service;

    @Mock
    private BookRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<Book> list = input.mockEntityList();


        when(repository.findAll()).thenReturn(list);

        var books = service.findAllBooks();

        assertNotNull(books);
        assertEquals(14, books.size());

        var bookOne = books.get(1);

        assertNotNull(bookOne);
        assertNotNull(bookOne.getKey());
        assertNotNull(bookOne.getLinks());
        System.out.println(bookOne.toString());
        assertTrue(bookOne.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", bookOne.getAuthor());
        assertEquals(Date.from(instant), bookOne.getLaunchDate());
        assertEquals(1D, bookOne.getPrice());
        assertEquals("Title Test1", bookOne.getTitle());

        var bookFour = books.get(4);

        assertNotNull(bookFour);
        assertNotNull(bookFour.getKey());
        assertNotNull(bookFour.getLinks());
        assertTrue(bookFour.toString().contains("links: [</api/books/v1/4>;rel=\"self\"]"));
        assertEquals("Author Test4", bookFour.getAuthor());
        assertEquals(Date.from(instant), bookFour.getLaunchDate());
        assertEquals(4D, bookFour.getPrice());
        assertEquals("Title Test4", bookFour.getTitle());
    }

    @Test
    void findById() {
        Book book = input.mockEntity();
        book.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(book));

        var result = service.findById(1);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(Date.from(instant), result.getLaunchDate());
        assertEquals(1D, result.getPrice());
        assertEquals("Title Test1", result.getTitle());
    }

    @Test
    void create() {
        Book entity = input.mockEntity(1);

        Book persisted = entity;
        persisted.setId(1);

        BookDTO dto = input.mockDTO(1);
        dto.setKey(1);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(dto);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(Date.from(instant), result.getLaunchDate());
        assertEquals(1D, result.getPrice());
        assertEquals("Title Test1", result.getTitle());

    }

    @Test
    void update() {
        Book entity = input.mockEntity(1);
        entity.setId(1);

        Book persisted = entity;
        persisted.setId(1);

        BookDTO dto = input.mockDTO(1);
        dto.setKey(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(dto);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(Date.from(instant), result.getLaunchDate());
        assertEquals(1D, result.getPrice());
        assertEquals("Title Test1", result.getTitle());
    }

    @Test
    void testCreateWithNullPerson(){
        Exception ex = assertThrows(RequiredObjectIsNullException.class, ()-> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = ex.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testUpdateWithNullPerson(){
        Exception ex = assertThrows(RequiredObjectIsNullException.class, ()-> {
            service.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = ex.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }
    @Test
    void delete() {
        Book book = input.mockEntity(1);
        book.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(book));

       service.delete(1);
    }
}