package br.com.jonathanestudo.apispring.services;

import br.com.jonathanestudo.apispring.controllers.PersonController;
import br.com.jonathanestudo.apispring.data.dto.v1.PersonDTO;
import br.com.jonathanestudo.apispring.exceptions.RequiredObjectIsNullException;
import br.com.jonathanestudo.apispring.exceptions.ResourceNotFoundException;
import br.com.jonathanestudo.apispring.mapper.ApiMapper;
import br.com.jonathanestudo.apispring.model.Person;
import br.com.jonathanestudo.apispring.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    private Logger logger = Logger.getLogger(PersonService.class.getName());


    public List<PersonDTO> findAll(){
        logger.info("Finding all people!");
        if(personRepository.findAll().size() == 0){
            throw new ResourceNotFoundException("No records found in the repository.");
        }
        var persons = ApiMapper.parseListObjects(personRepository.findAll(), PersonDTO.class);
        persons.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        return persons;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one person!");

        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));
        var dto = ApiMapper.parseObject(entity, PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return dto;
    }

    public PersonDTO create(PersonDTO person){
        logger.info("Creating one person!");
        if(person == null) throw new RequiredObjectIsNullException();
        var entity = ApiMapper.parseObject(person, Person.class);
        var dto = ApiMapper.parseObject(personRepository.save(entity), PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public PersonDTO update(PersonDTO person){
        logger.info("Updating one person!");
        if(person == null) throw new RequiredObjectIsNullException();
        var entity = personRepository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));

        if(entity.getFirstName() != null) entity.setFirstName(person.getFirstName());
        if(entity.getLastName() != null) entity.setLastName(person.getLastName());
        if(entity.getAddress() != null) entity.setAddress(person.getAddress());
        if(entity.getGender() != null) entity.setGender(person.getGender());

        var dto = ApiMapper.parseObject(personRepository.save(entity), PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting one person!");

        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));

        personRepository.delete(entity);
    }
}
