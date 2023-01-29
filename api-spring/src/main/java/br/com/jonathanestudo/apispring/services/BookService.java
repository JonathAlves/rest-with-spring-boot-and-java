package br.com.jonathanestudo.apispring.services;

import br.com.jonathanestudo.apispring.controllers.BookController;
import br.com.jonathanestudo.apispring.data.dto.v1.BookDTO;
import br.com.jonathanestudo.apispring.exceptions.ResourceNotFoundException;
import br.com.jonathanestudo.apispring.mapper.ApiMapper;
import br.com.jonathanestudo.apispring.model.Book;
import br.com.jonathanestudo.apispring.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    private Logger logger = Logger.getLogger(Book.class.getName());

    public List<BookDTO> findAllBooks(){
        logger.info("Finding all books");
        if(bookRepository.findAll().isEmpty()){
            throw new ResourceNotFoundException("No records found in the repository.");
        }
        var books = ApiMapper.parseListObjects(bookRepository.findAll(), BookDTO.class);
        books.forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));

        return books;
    }

    public BookDTO findById(Integer id){
        logger.info("finding one book");
        var entity = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        var bookDTO = ApiMapper.parseObject(entity, BookDTO.class);
        bookDTO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return bookDTO;
    }
}
