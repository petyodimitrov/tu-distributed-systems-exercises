package bg.tusofia.cst.ds.graphql.service;

import bg.tusofia.cst.ds.graphql.entity.Person;
import bg.tusofia.cst.ds.graphql.repository.PersonRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public Person createPerson(final String name, final int age) {
        final Person person = new Person(name, age);
        return personRepository.save(person);
    }

    @Transactional(readOnly = true)
    public List<Person> getAllPersons(final Integer count) {
        List<Person> result;
        if (count == null) {
            result = personRepository.findAll();
        } else {
            result = personRepository.findAll(PageRequest.of(0, count)).getContent();
        }
        return result;
    }

    @Transactional(readOnly = true)
    public Optional<Person> getPerson(final int id) {
        return personRepository.findById(id);
    }
}
