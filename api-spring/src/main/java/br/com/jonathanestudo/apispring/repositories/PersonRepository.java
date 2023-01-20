package br.com.jonathanestudo.apispring.repositories;

import br.com.jonathanestudo.apispring.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
