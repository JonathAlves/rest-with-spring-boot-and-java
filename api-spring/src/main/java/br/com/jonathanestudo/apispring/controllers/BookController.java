package br.com.jonathanestudo.apispring.controllers;

import br.com.jonathanestudo.apispring.data.dto.v1.BookDTO;
import br.com.jonathanestudo.apispring.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books/v1")
@Tag(name = "Books", description = "Endpoints for management of books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
    @Operation(summary = "Finds all books", description = "Finds all books",
    tags = {"Books"},
    responses = {
            @ApiResponse(description = "Success", responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))
                    )
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public List<BookDTO> findAll(){
        return bookService.findAllBooks();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
    @Operation(summary = "Finds one book", description = "Finds one book",
            tags = {"Books"},
            responses = {
                @ApiResponse(description = "Success", responseCode = "200",
                     content = @Content(schema = @Schema(implementation = BookDTO.class))),
                @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public BookDTO findById(@PathVariable(value = "id")Integer id){
        return bookService.findById(id);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"},
                consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
    @Operation(summary = "Adds a new book", description = "Adds a new book",
    tags = {"Books"},
    responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                content = @Content(schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public BookDTO create(@RequestBody BookDTO bookDTO){
        return bookService.create(bookDTO);
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
    @Operation(summary = "Updates one book", description = "Updates one book",
    tags = {"Books"},
    responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                content = @Content(schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public BookDTO update(@RequestBody BookDTO bookDTO){
        return bookService.update(bookDTO);
    }

    @DeleteMapping(value ="/{id}")
    @Operation(summary = "Deletes one book", description = "Deletes one book",
    responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<?> delete(@PathVariable Integer id){
        bookService.delete(id);
        return ResponseEntity.noContent().build();

    }
}
