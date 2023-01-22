package br.com.jonathanestudo.apispring.services;

import br.com.jonathanestudo.apispring.data.vo.v1.PersonDTO;
import br.com.jonathanestudo.apispring.exceptions.ResourceNotFoundException;
import br.com.jonathanestudo.apispring.mapper.ApiMapper;
import br.com.jonathanestudo.apispring.model.Person;
import br.com.jonathanestudo.apispring.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ApiMapper.parseListObjects(personRepository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id){
        logger.info("Finding one person!");

        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id."));

        return ApiMapper.parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person){
        logger.info("Creating one person!");
        var entity = ApiMapper.parseObject(person, Person.class);
        return ApiMapper.parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person){
        logger.info("Updating one person!");

        var entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));

        if(entity.getFirstName() != null) entity.setFirstName(person.getFirstName());
        if(entity.getLastName() != null) entity.setLastName(person.getLastName());
        if(entity.getAddress() != null) entity.setAddress(person.getAddress());
        if(entity.getGender() != null) entity.setGender(person.getGender());

        return ApiMapper.parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public void delete(Long id){
        logger.info("Deleting one person!");

        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));

        personRepository.delete(entity);
    }
}
