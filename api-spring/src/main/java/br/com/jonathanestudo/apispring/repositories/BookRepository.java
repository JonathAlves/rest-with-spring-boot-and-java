package br.com.jonathanestudo.apispring.repositories;

import br.com.jonathanestudo.apispring.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
