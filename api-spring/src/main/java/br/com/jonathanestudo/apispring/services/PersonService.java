package br.com.jonathanestudo.apispring.services;

import br.com.jonathanestudo.apispring.exceptions.ResourceNotFoundException;
import br.com.jonathanestudo.apispring.model.Person;
import br.com.jonathanestudo.apispring.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    private Logger logger = Logger.getLogger(PersonService.class.getName());


    public List<Person> findAll(){
        logger.info("Finding all people!");

        return personRepository.findAll();
    }

    public Person findById(Long id){
        logger.info("Finding one person!");

        return personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
    }

    public Person create(Person person){
        logger.info("Creating one person!");

        return personRepository.save(person);
    }

    public Person update(Person person){
        logger.info("Updating one person!");

        var entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        if(entity.getFirstName() != null) entity.setFirstName(person.getFirstName());
        if(entity.getLastName() != null) entity.setLastName(person.getLastName());
        if(entity.getAddress() != null) entity.setAddress(person.getAddress());
        if(entity.getGender() != null) entity.setGender(person.getGender());

        return personRepository.save(entity);
    }

    public void delete(Long id){
        logger.info("Deleting one person!");

        Person entity = findById(id);
        personRepository.delete(entity);
    }
}
