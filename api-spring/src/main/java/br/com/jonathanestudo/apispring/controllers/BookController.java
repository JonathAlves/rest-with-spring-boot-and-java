package br.com.jonathanestudo.apispring.controllers;

import br.com.jonathanestudo.apispring.data.dto.v1.BookDTO;
import br.com.jonathanestudo.apispring.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books/v1")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
    public List<BookDTO> findAll(){
        return bookService.findAllBooks();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
    public BookDTO findById(@PathVariable(value = "id")Integer id){
        return bookService.findById(id);
    }
}
