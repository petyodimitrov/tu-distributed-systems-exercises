package bg.tusofia.cst.ds.graphql.query;

import bg.tusofia.cst.ds.graphql.entity.Person;
import bg.tusofia.cst.ds.graphql.service.PersonService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonQuery implements GraphQLQueryResolver {

    private final PersonService personService;

    public PersonQuery(PersonService personService) {
        this.personService = personService;
    }

    public List<Person> getAllPersons(final Integer count) {
        return personService.getAllPersons(count);
    }

    public Optional<Person> getPerson(final int id) {
        return personService.getPerson(id);
    }

}
