package br.com.jonathanestudo.apispring.services;

import br.com.jonathanestudo.apispring.controllers.PersonController;
import br.com.jonathanestudo.apispring.data.dto.v1.PersonDTO;
import br.com.jonathanestudo.apispring.exceptions.RequiredObjectIsNullException;
import br.com.jonathanestudo.apispring.exceptions.ResourceNotFoundException;
import br.com.jonathanestudo.apispring.mapper.ApiMapper;
import br.com.jonathanestudo.apispring.model.Person;
import br.com.jonathanestudo.apispring.repositories.PersonRepository;
import br.com.jonathanestudo.apispring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private Logger logger = Logger.getLogger(UserService.class.getName());

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one person by username!");
        var user = userRepository.findByUsername(username);
        if (user != null) return user;
        else throw new UsernameNotFoundException("Username" + username + "not found");
    }
}
