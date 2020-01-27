package bg.tusofia.cst.ds.graphql.mutation;

import bg.tusofia.cst.ds.graphql.entity.Person;
import bg.tusofia.cst.ds.graphql.service.PersonService;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class PersonMutation implements GraphQLMutationResolver {

    private final PersonService personService;

    public PersonMutation(PersonService personService) {
        this.personService = personService;
    }

    public Person createPerson(final String name, final int age) {
        return personService.createPerson(name, age);
    }
}
