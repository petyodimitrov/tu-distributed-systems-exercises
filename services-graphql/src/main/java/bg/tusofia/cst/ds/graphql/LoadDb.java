package bg.tusofia.cst.ds.graphql;

import bg.tusofia.cst.ds.graphql.entity.Person;
import bg.tusofia.cst.ds.graphql.repository.PersonRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
public class LoadDb implements CommandLineRunner {

    private final PersonRepository personRepository;

    public LoadDb(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing database ...");
        ObjectMapper mapper = new ObjectMapper();
        InputStream jsonInputStream = new ClassPathResource("sampledata.json").getInputStream();

        List<Person> persons = mapper.readValue(jsonInputStream, new TypeReference<List<Person>>() {
        });
        personRepository.saveAll(persons);
        log.info("Database initialized successfully.");
    }
}
