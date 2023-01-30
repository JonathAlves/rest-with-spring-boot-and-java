package br.com.jonathanestudo.apispring.unittests.mapper.mocks;

import br.com.jonathanestudo.apispring.data.dto.v1.BookDTO;
import br.com.jonathanestudo.apispring.model.Book;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {
    LocalDateTime localDateTime = LocalDateTime.parse("2019-11-15T13:15:30");
    Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    public Book mockEntity() {
        return mockEntity(1);
    }

    public BookDTO mockDTO() {
        return mockDTO(1);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setId(number);
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(Date.from(instant));
        book.setPrice(Double.parseDouble(number.toString()));
        book.setTitle("Title Test" + number);
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setKey(number);
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(Date.from(instant));
        book.setPrice(Double.parseDouble(number.toString()));
        book.setTitle("Title Test" + number);
        return book;
    }
}
