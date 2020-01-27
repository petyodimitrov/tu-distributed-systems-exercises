package bg.tusofia.cst.ds.graphql.repository;

import bg.tusofia.cst.ds.graphql.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
